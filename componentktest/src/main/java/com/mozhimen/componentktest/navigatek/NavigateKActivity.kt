package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.extsk.e
import com.mozhimen.basick.extsk.et
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding
import com.mozhimen.componentktest.navigatek.fragments.FirstFragment
import com.mozhimen.componentktest.navigatek.fragments.SecondFragment

class NavigateKActivity : BaseKActivity<ActivityNavigatekBinding, NavigateKViewModel>(R.layout.activity_navigatek) {

    private var _currentItemId: Int = 0

    companion object {
        private const val navigatek_saved_current_id = "navigatek_saved_current_id"
    }

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemId = savedInstanceState.getInt(navigatek_saved_current_id)
        }
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val navController = NavigateK.buildNavGraph(this, R.id.navigatek_fragment_container, listOf(FirstFragment::class.java, SecondFragment::class.java))
        vm.liveFragmentId.observe(this) {
            if (it != null && navController.findDestination(it) != null) {
                navController.navigate(it)
                _currentItemId = it
            } else {
                "please add this destination to list".et(TAG)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(navigatek_saved_current_id, _currentItemId)
        super.onSaveInstanceState(outState)
    }
}