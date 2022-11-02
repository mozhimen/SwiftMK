package com.mozhimen.abilityktest.opencvk

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.opencvk.OpenCVKContrast
import com.mozhimen.abilityktest.R
import com.mozhimen.abilityktest.databinding.ActivityOpencvkContrastBinding
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.cropBitmap
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapConv
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapTrans
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.APermissionK
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@APermissionK(permissions = [Manifest.permission.CAMERA])
class OpenCVKContrastActivity : BaseKActivityVB<ActivityOpencvkContrastBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        _orgBitmap = UtilKRes.getDrawable(R.mipmap.opencvk_contrast_test)!!.drawable2Bitmap()
        require(OpenCVK.initSDK()) { "opencv init fail" }
        initCamera()
    }

    private fun initCamera() {
        vb.opencvkContrastPreview.initCamera(this, ACameraXKFacing.BACK)
        vb.opencvkContrastPreview.setImageAnalyzer(_frameAnalyzer)
        vb.opencvkContrastPreview.startCamera()
    }

    private lateinit var _orgBitmap: Bitmap

    private val _frameAnalyzer: ImageAnalysis.Analyzer by lazy {
        object : ImageAnalysis.Analyzer {
            private val _reentrantLock = ReentrantLock()

            @SuppressLint("UnsafeOptInUsageError")
            override fun analyze(image: ImageProxy) {
                try {
                    _reentrantLock.lock()
                    val bitmap: Bitmap = if (image.format == ImageFormat.YUV_420_888) {
                        ImageConverter.yuv2Bitmap(image)!!
                    } else {
                        ImageConverter.jpeg2Bitmap(image)
                    }
                    val rotateBitmap = UtilKBitmapConv.rotateBitmap(bitmap, 90)
                    val ratio: Double =
                        vb.opencvkContrastQrscan.getRectSize().toDouble() / UtilKScreen.getScreenWidth().toDouble()

                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )

                    val cropSameBitmap = UtilKBitmapConv.scaleSameSize(cropBitmap, _orgBitmap)
                    runOnUiThread {
                        vb.opencvkContrastImg.setImageBitmap(rotateBitmap)
                        vb.opencvkContrastImg1.setImageBitmap(cropSameBitmap.first)
                        vb.opencvkContrastImg2.setImageBitmap(cropSameBitmap.second)
                    }
                    //detect
                    val result = OpenCVKContrast.similarity(cropSameBitmap.first, cropSameBitmap.second) * 100
                    runOnUiThread {
                        vb.opencvkContrastRes.text = result.toString()
                    }
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }
}