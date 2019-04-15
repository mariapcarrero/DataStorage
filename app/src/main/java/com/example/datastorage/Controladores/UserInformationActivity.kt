package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.datastorage.R
import com.example.datastorage.Modelos.User
import com.google.gson.Gson
import android.view.View

class UserInformationActivity : AppCompatActivity() {

    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        val data = this.intent.getStringExtra("user")
        user = Gson().fromJson(data, User::class.javaObjectType)

        findViewById<TextView>(R.id.nameProfileInfo).text = user.name
        findViewById<TextView>(R.id.ageProfileInfo).text = user.age.toString()
        findViewById<TextView>(R.id.emailProfileInfo).text = user.email
    }

    fun goBack(view : View) {
        val intent = Intent(this, UsersListActivity::class.java)
        startActivity(intent)
    }
}
