package com.example.datastorage.Servicios

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionService {

    companion object {
        const val ACCEPTANCE_MESSAGE = "Se aceptaron todos los permisos."
        const val DENIED_MESSAGE = "Se nego alguno de los permisos."
    }

    private val context : Context
    private val builder : PermissionBuilder

    constructor(context: Context, builder: PermissionBuilder) {
        this.context = context
        this.builder = builder
    }

    fun requestPermissions(activity : Activity) {
        Dexter.withActivity(activity).
            withPermissions(builder.getPermissions())
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(context, ACCEPTANCE_MESSAGE,  Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, DENIED_MESSAGE,  Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).onSameThread().check()
    }
}