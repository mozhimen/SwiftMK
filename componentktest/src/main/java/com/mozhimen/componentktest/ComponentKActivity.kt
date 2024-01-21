package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.componentktest.mediak.MediaKAudioActivity
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.installk.InstallKActivity
import com.mozhimen.componentktest.mediak.MediaKActivity
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentktest.netk.NetKActivity
import com.mozhimen.componentktest.mediak.MediaKVideoActivity

class ComponentKActivity : BaseActivityVB<ActivityComponentkBinding>() {
    fun goInstallK(view: View) {
        startContext<InstallKActivity>()
    }

    fun goMediaK(view: View) {
        startContext<MediaKActivity>()
    }

    fun goNavigateK(view: View) {
        startContext<NavigateKActivity>()
    }

    fun goNetK(view: View) {
        startContext<NetKActivity>()
    }
}