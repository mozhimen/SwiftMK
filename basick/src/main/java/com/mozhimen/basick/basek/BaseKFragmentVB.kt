package com.mozhimen.basick.basek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.basek.commons.IBaseKActivity
import com.mozhimen.basick.utilk.UtilKViewDataBinding

abstract class BaseKFragmentVB<VB : ViewDataBinding>(
    private val _factory: ViewModelProvider.Factory? = null
) : Fragment(), IBaseKActivity {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    private var _vb: VB? = null
    protected val vb get() = _vb!!

    fun isAlive(): Boolean = !isRemoving && !isDetached && activity != null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _vb = UtilKViewDataBinding.get<VB>(this::class.java, inflater, container, 0).apply {
            lifecycleOwner = this@BaseKFragmentVB
        }
        return vb.root
    }

    /**
     * 及时释放vb避免内存泄漏
     */
    override fun onDestroyView() {
        vb.unbind()
        _vb = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData(savedInstanceState)
    }

    override fun initFlag() {

    }

    override fun initLayout() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}