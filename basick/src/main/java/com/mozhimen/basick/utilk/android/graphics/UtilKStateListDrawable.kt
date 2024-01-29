package com.mozhimen.basick.utilk.android.graphics

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKRes

/**
 * @ClassName UtilKStateListDrawable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/26 13:45
 * @Version 1.0
 */
object UtilKStateListDrawable {
    @JvmStatic
    fun get(drawableNormal: Drawable, drawablePressed: Drawable): StateListDrawable =
        StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), drawablePressed)
            addState(intArrayOf(-android.R.attr.state_pressed), drawableNormal)
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    fun get(@DrawableRes drawableNormalResId: Int, @DrawableRes drawablePressedResId: Int): StateListDrawable? {
        val drawableNormal = UtilKRes.getDrawable(drawableNormalResId)
        val drawablePressed = UtilKRes.getDrawable(drawablePressedResId)
        return if (drawableNormal != null && drawablePressed != null) get(drawableNormal, drawablePressed) else null
    }

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @AManifestKRequire(CPermission.INTERNET)
    fun getForStrUrls(strUrlDrawableNormal: String, strUrlDrawablePressed: String): Drawable? {
        val drawableNormal = UtilKDrawable.getDrawableForStrUrl(strUrlDrawableNormal)
        val drawablePressed = UtilKDrawable.getDrawableForStrUrl(strUrlDrawablePressed)
        return if (drawableNormal != null && drawablePressed != null) {
            get(drawableNormal, drawablePressed)
        } else null
    }
}