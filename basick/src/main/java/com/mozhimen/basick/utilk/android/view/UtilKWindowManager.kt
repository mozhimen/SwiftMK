package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.Display
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.android.content.UtilKContext


/**
 * @ClassName UtilKWindowManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:33
 * @Version 1.0
 */
object UtilKWindowManager {

    @JvmStatic
    fun get(context: Context): WindowManager =
        UtilKContext.getWindowManager(context)

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun getCurrentWindowMetrics(context: Context): WindowMetrics {
        return get(context).currentWindowMetrics
    }

    @JvmStatic
    fun getDefaultDisplay(context: Context): Display =
        get(context).defaultDisplay

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun getBounds(context: Context): Rect =
        getCurrentWindowMetrics(context).bounds

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun getBoundsWidth(context: Context): Int =
        getBounds(context).width()

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun getBoundsHeight(context: Context): Int =
        getBounds(context).height()
}