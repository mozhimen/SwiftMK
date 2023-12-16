package com.mozhimen.componentk.navigatek

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread
import com.mozhimen.componentk.navigatek.helpers.getDestinationId
import com.mozhimen.componentk.navigatek.helpers.startDestinationId
import com.mozhimen.componentk.navigatek.mos.MNavigateKConfig

/**
 * @ClassName NavigateKProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/17 16:44
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class NavigateKFragmentProxy<A>(
    private val _activity: FragmentActivity,
    private val _fragment: A,
    private val _containerId: Int,
    private val _classes: List<Class<*>>,
    private val _navigateKConfig: MNavigateKConfig = MNavigateKConfig(),
    private val _defaultDestinationId: Int = _classes.getOrNull(0)?.getDestinationId() ?: 0
) : BaseWakeBefDestroyLifecycleObserver() where A : Fragment, A : LifecycleOwner {
    ///////////////////////////////////////////////////////////////////////////////////////

    private val _navigateKFragment: NavigateKFragment by lazy { NavigateKFragment(_activity, _fragment) }
    private var _navController: NavController? = null

    ///////////////////////////////////////////////////////////////////////////////////////

    val navController get() = _navController!!

    ///////////////////////////////////////////////////////////////////////////////////////

    init {
        _activity.runOnMainThread(::init)
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    fun startDestinationId(destinationId: Int) {
        _navController?.startDestinationId(destinationId)
    }

    override fun onPause(owner: LifecycleOwner) {
        _navController = null
        super.onDestroy(owner)
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private fun init() {
        _navController = _navigateKFragment.setNavigateKConfig(_navigateKConfig).setupNavGraph(_containerId, _classes)
    }
}