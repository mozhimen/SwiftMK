package com.mozhimen.basick.elemk.androidx.fragment.bases.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IFragment
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragment
import com.mozhimen.basick.utilk.androidx.fragment.UtilKFragment
import com.mozhimen.basick.utilk.androidx.viewbinding.UtilKViewBinding

open class BaseFragmentVB<VB : ViewBinding> : BaseFragment(), IActivity, IFragment {

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewBinding.get(this::class.java, layoutInflater/*, 0*/)
    }

    //////////////////////////////////////////////////////////////////////////////

    fun isAlive(): Boolean = UtilKFragment.isAlive(this)

    //////////////////////////////////////////////////////////////////////////////

    //@warn 如果子类可以继承, 这里子类的VB一定要放置在第一个
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflateView(container)
        return vb.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initLayout()
            initData(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun inflateView(viewGroup: ViewGroup?) {

    }

    override fun initFlag() {

    }

    override fun initLayout() {

    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initObserver() {

    }
}