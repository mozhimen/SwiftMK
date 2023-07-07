package com.mozhimen.basicktest.sensek

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.sensek.net_conn.SenseKNetConnDelegate
import com.mozhimen.basick.sensek.net_conn.commons.INetConnListener
import com.mozhimen.basick.sensek.net_conn.cons.ENetType
import com.mozhimen.basick.utilk.android.net.UtilKNetConn
import com.mozhimen.basicktest.databinding.ActivitySensekNetConnBinding


/**
 * @ClassName NetKConnActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:36
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
@APermissionCheck(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
class NetConnActivity : BaseActivityVB<ActivitySensekNetConnBinding>() {
    @OptIn(AOptLazyInit::class)
    private val _netConnDelegate: SenseKNetConnDelegate<NetConnActivity> by lazy { SenseKNetConnDelegate(this, _netKConnListener).apply { bindLifecycle(this@NetConnActivity) } }
    private val _netKConnListener = object : INetConnListener {
        override fun onDisconnected() {
            vb.netkConnTxt.text = "断网了"
        }

        @SuppressLint("SetTextI18n")
        override fun onConnected(type: ENetType) {
            val str =
                when (type) {
                    ENetType.WIFI -> {
//                        if (ActivityCompat.checkSelfPermission(this@NetConnActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            ""
//                        } else
                            "WIFI risi ${UtilKNetConn.getWifiStrength()}"
                    }

                    ENetType.M4G, ENetType.M2G, ENetType.M3G -> {
                        "移动网"
                    }

                    else -> "其他"
                }
            vb.netkConnTxt.text = "联网了 type $str"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        _netConnDelegate.bindLifecycle(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this, onSuccess = {
            super.initData(savedInstanceState)
        })
    }


}