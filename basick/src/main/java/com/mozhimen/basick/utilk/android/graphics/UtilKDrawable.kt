package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap

/**
 * @ClassName UtilKDrawable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/22 22:42
 * @Version 1.0
 */
fun Drawable.applyColorFilter(@ColorInt colorInt: Int) {
    UtilKDrawable.applyColorFilter(this, colorInt)
}

fun Drawable.drawable2bitmap(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight, config: Bitmap.Config? = null): Bitmap =
    UtilKDrawable.drawable2bitmap(this, width, height, config)

fun Drawable.drawable2bitmap2(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight): Bitmap =
    UtilKDrawable.drawable2bitmap2(this, width, height)

object UtilKDrawable {
    @JvmStatic
    fun drawable2bitmap(drawable: Drawable, width: Int = drawable.intrinsicWidth, height: Int = drawable.intrinsicHeight, config: Bitmap.Config? = null): Bitmap =
        drawable.toBitmap(width, height, config)

    @JvmStatic
    fun drawable2bitmap2(drawable: Drawable, width: Int = drawable.intrinsicWidth, height: Int = drawable.intrinsicHeight): Bitmap =
        if (drawable is BitmapDrawable) drawable.bitmap else {
            val bitmap: Bitmap = if (width <= 0 || height <= 0) {
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }

    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否正常的drawable
     * @param drawable Drawable
     * @return Boolean
     */
    @JvmStatic
    fun isColorDrawableNormal(drawable: Drawable): Boolean {
        return drawable !is ColorDrawable || drawable.color != Color.TRANSPARENT
    }

    /////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyColorFilter(drawable: Drawable, @ColorInt colorInt: Int) {
        drawable.mutate().setColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
    }
}