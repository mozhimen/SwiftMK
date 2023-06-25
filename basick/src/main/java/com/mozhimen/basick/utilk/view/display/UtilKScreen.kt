package com.mozhimen.basick.utilk.view.display

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.FloatRange
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.view.bar.UtilKVirtualBar
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.graphics.bitmap.UtilKBitmapDeal
import com.mozhimen.basick.utilk.res.UtilKConfiguration
import com.mozhimen.basick.utilk.res.UtilKDisplay
import com.mozhimen.basick.utilk.res.UtilKTheme
import com.mozhimen.basick.utilk.view.window.UtilKDecorView
import com.mozhimen.basick.utilk.view.window.UtilKWindow
import com.mozhimen.basick.utilk.view.window.UtilKWindowManager
import kotlin.math.sqrt

/**
 * @ClassName ScreenUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 9:16
 * @Version 1.0
 */
object UtilKScreen : BaseUtilK() {

    /**
     * 是否全屏
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        UtilKWindow.isFullScreen(activity)

    /**
     * 是否全屏
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreen(): Boolean =
        UtilKTheme.isFullScreen(_context)

    /**
     * 设置全屏
     * @param decorView View
     */
    @JvmStatic
    fun setFullScreen(decorView: View) {
        UtilKDecorView.setFullScreen(decorView)
    }

    /**
     * 设置屏幕亮度
     * @param paramFloat Float 0-1范围
     * @param activity Activity
     */
    @JvmStatic
    fun setScreenBrightness(@FloatRange(from = 0.0, to = 1.0) paramFloat: Float, activity: Activity) {
        val layoutParams: WindowManager.LayoutParams = UtilKWindow.getAttributes(activity)
        layoutParams.screenBrightness = paramFloat
        UtilKWindow.setAttributes(activity, layoutParams)
    }

    ////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕密度dp
     * @return Int
     */
    @JvmStatic
    fun getScreenDensityDpi(): Int =
        UtilKDisplay.getDensityDpi()

    /**
     * 获取屏幕密度dp
     * @return Int
     */
    @JvmStatic
    fun getScreenDensityDpi1(): Int =
        UtilKConfiguration.getDensityDpi()

    /**
     * 获取屏幕密度
     * @return Float
     */
    @JvmStatic
    fun getScreenDensity(): Float =
        UtilKDisplay.getDensity()

    /**
     * 获取屏幕宽度
     * @return Int
     */
    @JvmStatic
    fun getCurrentScreenWidth(): Int =
        UtilKDisplay.getWidthPixels()

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     * @return Int
     */
    @JvmStatic
    fun getCurrentScreenHeight(): Int =
        UtilKDisplay.getHeightPixels()

    /**
     * 获取像素宽
     * @return Int
     */
    @JvmStatic
    fun getRealScreenWidth(): Int =
        if (Build.VERSION.SDK_INT >= CVersionCode.V_30_11_R) {
            UtilKWindowManager.getBoundsWidth(_context)
        } else {
            val size = Point()
            UtilKWindowManager.getSize(_context, size)
            size.x
        }

    /**
     * 获取像素高
     * @return Int
     */
    @JvmStatic
    fun getRealScreenHeight(): Int =
        if (Build.VERSION.SDK_INT >= CVersionCode.V_30_11_R) {
            UtilKWindowManager.getBoundsHeight(_context)
        } else {
            val size = Point()
            UtilKWindowManager.getSize(_context, size)
            size.y
        }

    /**
     * 获取屏幕尺寸
     * @return Float
     */
    @JvmStatic
    fun getScreenSize(): Float {
        val xdpi = UtilKDisplay.getXdpi()
        val ydpi = UtilKDisplay.getYdpi()
        val width = getCurrentScreenWidth()
        val height = getCurrentScreenHeight()
        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt(widthPhy + heightPhy)
    }

    /**
     * 获取dp宽
     * @return Int
     */
    @JvmStatic
    fun getScreenWidthDp(): Int =
        UtilKConfiguration.getScreenWidthDp()

    /**
     * 获取dp高
     * @return Int
     */
    @JvmStatic
    fun getScreenHeightDp(): Int =
        UtilKConfiguration.getScreenHeightDp()

    /**
     * 获取屏幕方向
     * @return Int
     */
    @JvmStatic
    fun getScreenOrientation(): Int =
        UtilKConfiguration.getOrientation()

    /**
     * 是否为竖屏
     * @return Boolean
     */
    @JvmStatic
    fun isScreenPortrait(): Boolean =
        UtilKConfiguration.isOrientationPortrait()

    /**
     * 获取方向
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getScreenRotation(activity: Activity): Int =
        if (Build.VERSION.SDK_INT >= CVersionCode.V_30_11_R) {
            UtilKDisplay.getRotation(activity)
        } else {
            UtilKWindowManager.getRotation(activity)
        }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 截屏
     * @param activity Activity
     * @return Bitmap
     */
    @JvmStatic
    fun captureScreen(activity: Activity): Bitmap =
        UtilKBitmapDeal.getBitmapForScreen(activity)
}