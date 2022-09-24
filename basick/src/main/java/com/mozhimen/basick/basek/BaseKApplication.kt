package com.mozhimen.basick.basek

import android.app.Application
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.StackKMgr
import java.util.*

/**
 * @ClassName BaseKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:00
 * @Version 1.0
 */
open class BaseKApplication : Application() {
    val TAG = "${this.javaClass.simpleName}>>>>>"

    override fun onCreate() {
        super.onCreate()
        StackKMgr.instance
    }
}