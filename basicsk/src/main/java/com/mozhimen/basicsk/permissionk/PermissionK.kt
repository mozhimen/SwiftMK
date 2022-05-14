package com.mozhimen.basicsk.permissionk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basicsk.extsk.toJson
import com.mozhimen.basicsk.permissionk.annors.PermissionKAnnor
import com.mozhimen.basicsk.utilk.showToast

/**
 * @ClassName PermissionK
 * @Description TO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
object PermissionK {
    private val TAG = "PermissionK>>>>>"

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param isGranted Function1<Boolean, Unit>
     */
    fun initPermissions(
        activity: AppCompatActivity,
        isGranted: (Boolean) -> Unit,
    ) {
        val permissionAnnor = activity.javaClass.getAnnotation(PermissionKAnnor::class.java)
        requireNotNull(permissionAnnor) { TAG + "you may be forget add annor" }
        val permissions = permissionAnnor.permissions
        if (permissions.isNotEmpty()) {
            if (!checkPermissions(activity, *permissions)) {
                requestPermissions(activity, *permissions) { allGranted, deniedList ->
                    printDeniedList(deniedList)
                    isGranted(allGranted)
                }
            } else {
                isGranted(true)
            }
        } else {
            isGranted(true)
        }
    }

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param permissions Array<String>
     * @param isGranted Function1<Boolean, Unit>
     */
    fun initPermissions(
        activity: AppCompatActivity,
        permissions: Array<String>,
        isGranted: (Boolean) -> Unit
    ) {
        if (permissions.isNotEmpty()) {
            if (!checkPermissions(activity, *permissions)) {
                requestPermissions(activity, *permissions) { allGranted, deniedList ->
                    isGranted(false)
                }
            } else {
                isGranted(true)
            }
        } else {
            isGranted(true)
        }
    }

    /**
     * 作用: 批量申请动态权限
     * 用法: PermissionApplier.requestPermissions(this,Manifest.permission.CALL_PHONE,
     * ...){ allGranted,deniedList ->
     *     if(allGranted){ ... }
     *     else { ... }
     * }
     * @param activity FragmentActivity
     * @param permissions Array<out String>
     * @param callback Function2<Boolean, List<String>, Unit>
     */
    fun requestPermissions(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionKCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)
    }

    /**
     * 作用: 权限检查
     * @param activity Activity
     * @param permissions Array<out String>
     * @return Boolean
     */
    fun checkPermissions(activity: Activity, vararg permissions: String): Boolean {
        var allGranted = true
        return if (permissions.isEmpty()) {
            true
        } else {
            permissions.forEach {
                allGranted = allGranted and (ContextCompat.checkSelfPermission(
                    activity,
                    it
                ) == PackageManager.PERMISSION_GRANTED)
            }
            allGranted
        }
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    fun applySetting(activity: Activity) {
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }

    /**
     * 打印被拒绝的权限
     * @param deniedList List<String>
     */
    private fun printDeniedList(deniedList: List<String>) {
        "请在设置中打开${deniedList.toJson()}权限".showToast()
    }
}