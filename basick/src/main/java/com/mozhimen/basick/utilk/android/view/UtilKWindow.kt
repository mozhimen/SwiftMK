package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import com.mozhimen.basick.elemk.cons.CWinMgrLP
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKWindow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/27 23:14
 * @Version 1.0
 */
object UtilKWindow : BaseUtilK() {

    @JvmStatic
    fun get(activity: Activity): Window =
        activity.window

    //////////////////////////////////////////////////////////////////
    @JvmStatic
    fun getDecorView(activity: Activity): View =
        getDecorView(get(activity))

    @JvmStatic
    fun getDecorView(window: Window): View =
        window.decorView

    @JvmStatic
    fun getPeekDecorView(activity: Activity): View? =
        getPeekDecorView(get(activity))

    @JvmStatic
    fun getPeekDecorView(window: Window): View? =
        window.peekDecorView()

    @JvmStatic
    fun getContentView(activity: Activity): View? =
        getContentView(get(activity))

    @JvmStatic
    fun getContentView(window: Window): View? =
        window.findViewById(android.R.id.content)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setAttributes(activity: Activity, attributes: WindowManager.LayoutParams) {
        setAttributes(get(activity), attributes)
    }

    @JvmStatic
    fun setAttributes(window: Window, attributes: WindowManager.LayoutParams) {
        window.attributes = attributes
    }

    @JvmStatic
    fun getAttributes(activity: Activity): WindowManager.LayoutParams =
        getAttributes(get(activity))

    @JvmStatic
    fun getAttributes(window: Window): WindowManager.LayoutParams =
        window.attributes

    @JvmStatic
    fun getFlags(activity: Activity): Int =
        getFlags(get(activity))

    @JvmStatic
    fun getFlags(window: Window): Int =
        getAttributes(window).flags

    @JvmStatic
    fun clearFlags(activity: Activity, flags: Int) {
        get(activity).clearFlags(flags)
    }

    @JvmStatic
    fun addFlags(activity: Activity, flags: Int) {
        get(activity).addFlags(flags)
    }

    @JvmStatic
    fun setFlags(activity: Activity, flags: Int, mask: Int) {
        setFlags(get(activity), flags, mask)
    }

    @JvmStatic
    fun setFlags(window: Window, flags: Int, mask: Int) {
        window.setFlags(flags, mask)
    }

    @JvmStatic
    fun setStatusBarColor(activity: Activity, @ColorInt colorInt: Int) {
        get(activity).statusBarColor = colorInt
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setFullScreen(window: Window) {
        setFlags(window, CWinMgrLP.FLAG_FULLSCREEN, CWinMgrLP.FLAG_FULLSCREEN)
    }

    @JvmStatic
    fun isFullScreenInFlag(activity: Activity): Boolean =
        getFlags(activity) and CWinMgrLP.FLAG_FULLSCREEN != 0

    /**
     * 是否全屏
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreenInFlag2(activity: Activity): Boolean =
        getFlags(activity) and CWinMgrLP.FLAG_FULLSCREEN == CWinMgrLP.FLAG_FULLSCREEN

    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        isFullScreenInFlag2(activity) || !UtilKNavigationBar.isNavigationBarVisible(activity) || !UtilKStatusBar.isStatusBarVisible(activity)
}

