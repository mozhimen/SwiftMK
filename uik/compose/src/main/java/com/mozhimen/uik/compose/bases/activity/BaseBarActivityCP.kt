package com.mozhimen.uik.compose.bases.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import com.mozhimen.basick.bases.BaseBarActivity
import com.mozhimen.kotlin.elemk.commons.I_Listener
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper

/**
 * @ClassName BaseBarActivityCP
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2025/2/2 15:44
 * @Version 1.0
 */
abstract class BaseBarActivityCP : BaseBarActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initFlag()
            initLayout()
            initData(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "onCreate: e ${e.message}")
        }
    }

    override fun initLayout() {
        setContent(getCompositionContext(), getContent())
        super.initLayout()
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }

    ///////////////////////////////////////////////////////////////

    abstract fun getContent(): @Composable I_Listener

    ///////////////////////////////////////////////////////////////

    open fun getCompositionContext(): CompositionContext? = null
}