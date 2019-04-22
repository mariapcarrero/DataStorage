package com.example.datastorage.Servicios

import android.content.Intent
import android.support.v7.app.AppCompatActivity

interface ITemporarilyActivity<T : AppCompatActivity> {

    fun open(source : T)

    fun onActivityCanceled(source : T, requestCode: Int, resultCode: Int, data: Intent?)

    fun onActivityAccepted(source : T, requestCode: Int, resultCode: Int, data: Intent?)
}