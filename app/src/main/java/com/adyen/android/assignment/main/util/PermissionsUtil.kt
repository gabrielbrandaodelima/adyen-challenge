package com.adyen.android.assignment.main.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.*

object PermissionsUtil {
    fun checkPermissions(
        context: Activity,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        return try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }
            val permissionsToAsk: MutableList<String> = ArrayList()
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsToAsk.add(permission)
                }
            }
            if (permissionsToAsk.size > 0) {
                ActivityCompat.requestPermissions(
                    context,
                    permissionsToAsk.toTypedArray(),
                    requestCode
                )
                return false
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                return false
            }
        }
        return true
    }

    fun shouldShowRequestPermissionRationale(
        activity: Activity,
        vararg permissions: String
    ): Boolean {
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return false
            }
        }
        return true
    }

    fun shouldShowRequestPermissionRationale(
        fragment: Fragment,
        vararg permissions: String
    ): Boolean {
        for (permission in permissions) {
            if (!fragment.shouldShowRequestPermissionRationale(permission)) {
                return false
            }
        }
        return true
    }

    fun checkPermission(
        permissionRequest: Array<String>,
        grantResult: IntArray,
        permission: String
    ): Boolean {
        val index = Arrays.asList(*permissionRequest).indexOf(permission)
        return index != -1 && grantResult[index] == PackageManager.PERMISSION_GRANTED
    }
}