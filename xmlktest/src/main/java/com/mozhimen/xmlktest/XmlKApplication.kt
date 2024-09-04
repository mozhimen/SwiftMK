package com.mozhimen.xmlktest

import com.mozhimen.stackk.bases.BaseApplication
import com.mozhimen.kotlin.lintk.optins.OApiMultiDex_InApplication


/**
 * @ClassName XmlKApplication
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 15:11
 * @Version 1.0
 */
@OptIn(OApiMultiDex_InApplication::class)
class XmlKApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        //AdaptKAutoSize.instance.init(640, 400)
    }
}