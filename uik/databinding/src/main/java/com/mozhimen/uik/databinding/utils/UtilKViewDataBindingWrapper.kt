package com.mozhimen.uik.databinding.utils

import android.app.Dialog
import android.view.View
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBActivity
import com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBAny
import com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBDialog
import com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBFragment
import com.mozhimen.uik.databinding.impls.viewdatabinding.DelegateVDBView

/**
 * @ClassName UtilKViewDataBindingWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/28 23:18
 * @Version 1.0
 */
inline fun <reified VDB : ViewDataBinding> ComponentActivity.viewDataBinding(): com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBActivity<ComponentActivity, VDB> =
    UtilKViewDataBindingWrapper.viewDataBinding<VDB>(this)

inline fun <reified VDB : ViewDataBinding> Fragment.viewDataBinding(): com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBFragment<VDB> =
    UtilKViewDataBindingWrapper.viewDataBinding<VDB>(this)

inline fun <D, reified VDB : ViewDataBinding> D.viewDataBinding(): com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBDialog<D, VDB> where  D : Dialog, D : LifecycleOwner =
    UtilKViewDataBindingWrapper.viewDataBinding<D, VDB>(this)

inline fun <reified VDB : ViewDataBinding> LifecycleOwner.viewDataBinding(view: View): com.mozhimen.uik.databinding.impls.viewdatabinding.DelegateVDBView<VDB> =
    UtilKViewDataBindingWrapper.viewDataBinding(view, this)

//////////////////////////////////////////////////////////////////

object UtilKViewDataBindingWrapper {
    inline fun <reified VDB : ViewDataBinding> viewDataBinding(componentActivity: ComponentActivity): com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBActivity<ComponentActivity, VDB> =
        com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBActivity(VDB::class.java, componentActivity)

    inline fun <reified VDB : ViewDataBinding> viewDataBinding(fragment: Fragment): com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBFragment<VDB> =
        com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBFragment(VDB::class.java, fragment)

    inline fun <D, reified VDB : ViewDataBinding> viewDataBinding(dialog: D): com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBDialog<D, VDB> where D : Dialog, D : LifecycleOwner =
        com.mozhimen.uik.databinding.impls.viewbinding.DelegateVBDialog(VDB::class.java, dialog)

    inline fun <reified VDB : ViewDataBinding> viewDataBinding(view: View, obj: LifecycleOwner): com.mozhimen.uik.databinding.impls.viewdatabinding.DelegateVDBView<VDB> =
        com.mozhimen.uik.databinding.impls.viewdatabinding.DelegateVDBView(VDB::class.java, view, obj)
}