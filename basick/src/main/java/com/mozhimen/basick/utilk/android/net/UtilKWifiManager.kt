package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKWifiManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:12
 * @Version 1.0
 */
@OPermission_ACCESS_WIFI_STATE
@OPermission_ACCESS_FINE_LOCATION
object UtilKWifiManager {
    @JvmStatic
    fun get(context: Context): WifiManager =
        UtilKContext.getWifiManager(context)

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getScanResults(context: Context): List<ScanResult> =
        get(context).scanResults

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getConnectionInfo(context: Context): WifiInfo? =
        get(context).connectionInfo

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getConfiguredNetworks(context: Context): List<WifiConfiguration>? =
        get(context).configuredNetworks

    /////////////////////////////////////////////////////////////////////////////

    fun isWifiEnabled(context: Context): Boolean =
        get(context).isWifiEnabled
}