package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import com.example.datastorage.R
import com.example.datastorage.Modelos.User
import com.google.gson.Gson
import android.view.View
import android.graphics.BitmapFactory

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
        val img = user.image
        if (img != null) {
            findViewById<ImageView>(R.id.imageProfileInfo).setImageBitmap(
                BitmapFactory.decodeByteArray(img, 0, img.size)
            )
        }


    }

    fun goBack(view : View) {
        val intent = Intent(this, UsersListActivity::class.java)
        startActivity(intent)
    }
}
