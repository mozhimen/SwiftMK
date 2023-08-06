package com.mozhimen.basick.utilk.android.graphics

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory.Options
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.graphics.drawable.toDrawable
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.UtilKContextCompat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.android.content.anyBitmap2imageFile
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2bytes
import com.mozhimen.basick.utilk.java.io.file2strFilePath
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.kotlin.hasSpace
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


/**
 * @ClassName UtilKBitmapFormat
 * @Description Bitmap bytes 流转换类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */
fun ByteArray.anyBytes2anyBitmap(): Bitmap =
    UtilKBitmapFormat.anyBytes2anyBitmap(this)

fun Bitmap.anyBitmap2jpegBytes(): ByteArray? =
    UtilKBitmapFormat.anyBitmap2jpegBytes(this)

fun Bitmap.anyBitmap2rgb565Bitmap(): Bitmap =
    UtilKBitmapFormat.anyBitmap2rgb565Bitmap(this)

fun Bitmap.jpegBitmap2jpegFile(filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.jpegBitmap2jpegFile(this, filePathWithName, quality)

fun Bitmap.anyBitmap2anyBytes(compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray? =
    UtilKBitmapFormat.anyBitmap2anyBytes(this, compressFormat, quality)

fun String.strFilePath2anyBitmap(): Bitmap? =
    UtilKBitmapFormat.strFilePath2anyBitmap(this)

fun String.strFilePath2anyBitmap(opts: Options): Bitmap? =
    UtilKBitmapFormat.strFilePath2anyBitmap(this, opts)

//////////////////////////////////////////////////////////////////////////////////////////

object UtilKBitmapFormat : BaseUtilK() {

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName)

    @JvmStatic
    fun file2anyBitmap(file: File): Bitmap? =
        strFilePath2anyBitmap(file.file2strFilePath())

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String, opts: Options): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName, opts)

    @JvmStatic
    fun file2anyBitmap(file: File, opts: Options): Bitmap? =
        strFilePath2anyBitmap(file.file2strFilePath(), opts)

    /**
     * 保存图片 before 29
     */
    @JvmStatic
    fun anyBitmap2file(sourceBitmap: Bitmap, filePathWithName: String, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        anyBitmap2file(sourceBitmap, File(filePathWithName), compressFormat, quality)

    /**
     * 保存图片 before 29
     */
    @JvmStatic
    fun anyBitmap2file(sourceBitmap: Bitmap, file: File, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? {
        UtilKFile.createFile(file)
        var fileOutputStream: FileOutputStream? = null
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file, false)
            bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            sourceBitmap.applyAnyBitmapCompress(compressFormat, quality, bufferedOutputStream)
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedOutputStream?.flushClose()
            fileOutputStream?.flushClose()
        }
        return null
    }

    @SuppressLint("MissingPermission")
    @JvmStatic
    fun jpegBitmap2jpegFile(sourceBitmap: Bitmap, filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            if (UtilKContextCompat.isSelfPermissionGranted(_context, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                sourceBitmap.anyBitmap2imageFile(filePathWithName, CompressFormat.JPEG, quality)
            else null
        } else anyBitmap2jpegFile(sourceBitmap, filePathWithName, quality)

    @JvmStatic
    fun anyBitmap2jpegFile(sourceBitmap: Bitmap, filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        anyBitmap2file(sourceBitmap, filePathWithName, CompressFormat.JPEG, quality)

    @JvmStatic
    fun anyBitmap2pngFile(sourceBitmap: Bitmap, filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        anyBitmap2file(sourceBitmap, filePathWithName, CompressFormat.PNG, quality)

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun anyBytes2anyBitmap(bytes: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    @JvmStatic
    fun anyBitmap2anyBytes(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray {
        ByteArrayOutputStream(sourceBitmap.width * sourceBitmap.height * 4).flushClose {
            sourceBitmap.applyAnyBitmapCompress(compressFormat, quality, it)
            return it.byteArrayOutputStream2bytes()
        }
    }

    @JvmStatic
    fun anyBitmap2jpegBytes(sourceBitmap: Bitmap, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
        anyBitmap2anyBytes(sourceBitmap, CompressFormat.JPEG, quality)

    @JvmStatic
    fun anyBitmap2pngBytes(sourceBitmap: Bitmap, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
        anyBitmap2anyBytes(sourceBitmap, CompressFormat.PNG, quality)

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun anyBitmap2rgb565Bitmap(sourceBitmap: Bitmap): Bitmap =
        sourceBitmap.copy(Bitmap.Config.RGB_565, true)

    @JvmStatic
    fun anyBitmap2drawable(sourceBitmap: Bitmap): Drawable =
        sourceBitmap.toDrawable(UtilKResource.getSystemResources())

    @JvmStatic
    fun anyBitmap2drawable2(sourceBitmap: Bitmap): Drawable =
        BitmapDrawable(UtilKResource.getAppResources(_context), sourceBitmap)
}