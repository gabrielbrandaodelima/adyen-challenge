package com.adyen.android.assignment.main.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.adyen.android.assignment.R

internal class DialogPermissionRationale : AppCompatDialogFragment() {

    companion object {
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_MESSAGE = "ARG_MESSAGE"
        private const val ARG_PERMISSIONS = "ARG_PERMISSIONS"
        private const val ARG_REQUEST_CODE = "ARG_REQUEST_CODE"
        private const val ARG_GOTO_APP_CONFIG = "ARG_GOTO_APP_CONFIG"
    }

    private lateinit var title: String
    private lateinit var message: String
    private lateinit var permissions: Array<String>
    private var requestCode: Int = 0
    private var goToAppConfig: Boolean = false

    var listener: RequestPermission? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(ARG_TITLE) ?: getString(R.string.permission_needed)
        message = arguments?.getString(ARG_MESSAGE)
            ?: getString(R.string.permission_needed)
        goToAppConfig = arguments?.getBoolean(ARG_GOTO_APP_CONFIG) ?: false
        requestCode = arguments?.getInt(ARG_REQUEST_CODE)!!
        permissions = arguments?.getStringArray(ARG_PERMISSIONS)!!

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton(R.string.OK) { dialog, _ ->
                    if (goToAppConfig) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", context.packageName, null)
                        intent.data = uri
                        context.startActivity(intent)
                    } else {
                        listener?.requestPermissions(permissions, requestCode)
                    }
                    dialog.dismiss()
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null.")
    }


    class Builder {

        private val context: Context
        private val bundle = Bundle()
        private val listener: RequestPermission

        constructor(activity: Activity, permissions: Array<String>, requestCode: Int) {
            bundle.putInt(ARG_REQUEST_CODE, requestCode)
            bundle.putStringArray(ARG_PERMISSIONS, permissions)
            context = activity
            listener = object : RequestPermission {
                override fun requestPermissions(permissions: Array<String>, requestCode: Int) {
                    ActivityCompat.requestPermissions(activity, permissions, requestCode)
                }
            }
        }

        constructor(fragment: Fragment, permissions: Array<String>, requestCode: Int) {
            bundle.putInt(ARG_REQUEST_CODE, requestCode)
            bundle.putStringArray(ARG_PERMISSIONS, permissions)
            context = fragment.requireActivity()
            listener = object : RequestPermission {
                override fun requestPermissions(permissions: Array<String>, requestCode: Int) {
                    fragment.requestPermissions(permissions, requestCode)
                }
            }
        }


        fun setTitle(title: String): Builder {
            bundle.putString(ARG_TITLE, title)
            return this
        }

        fun setTitle(@StringRes title: Int): Builder {
            setTitle(context.getString(title))
            return this
        }

        fun setMessage(message: String): Builder {
            bundle.putString(ARG_MESSAGE, message)
            return this
        }

        fun setMessage(@StringRes message: Int): Builder {
            setMessage(context.getString(message))
            return this
        }

        fun goToAppConfig(goToAppConfig: Boolean): Builder {
            bundle.putBoolean(ARG_GOTO_APP_CONFIG, goToAppConfig)
            return this
        }

        fun build(): DialogFragment {
            AlertDialog.Builder(context)
            val dialog = DialogPermissionRationale()
            dialog.arguments = bundle
            dialog.listener = listener
            return dialog
        }
    }

    interface RequestPermission {
        fun requestPermissions(permissions: Array<String>, requestCode: Int)
    }
}