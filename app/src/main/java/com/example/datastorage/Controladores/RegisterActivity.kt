package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.R
import com.example.datastorage.Servicios.LoginServices
import com.example.datastorage.Modelos.User
import android.Manifest
import com.example.datastorage.Servicios.GalleryServiceForRegister
import com.example.datastorage.Servicios.PermissionBuilder
import com.example.datastorage.Servicios.PermissionService

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val USER_ALREADY_EXISTS = "El usuario ya existe en el sistema."
        const val USER_REGISTERED_SUCCESSFULLY = "Se creo exitosamente el usuario."
    }

    private lateinit var loginServices : LoginServices
    private lateinit var permissionServices: PermissionService
    private lateinit var galleryServiceForRegister: GalleryServiceForRegister

    private lateinit var imgArr : ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginServices= LoginServices(this)

        permissionServices = PermissionService(this, PermissionBuilder().addPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
        permissionServices.requestPermissions(this)

        galleryServiceForRegister = GalleryServiceForRegister()
    }

    fun uploadPhoto(view : View?) {
        galleryServiceForRegister.open(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_CANCELED) {
            galleryServiceForRegister.onActivityCanceled(this, requestCode, resultCode, data)
            return
        }
        galleryServiceForRegister.onActivityAccepted(this, requestCode, resultCode, data)
    }

    fun createProfile(view : View) {
        val name = findViewById<TextView>(R.id.nombre).text.toString()
        val email = findViewById<TextView>(R.id.correo).text.toString()
        val password = findViewById<TextView>(R.id.contraseña).text.toString()
        val age = findViewById<TextView>(R.id.edad).text.toString().toInt()

        val user = User(null, name, email, age, password, imgArr)
        val exists = this.loginServices.existsUser(user)
        if (exists) {
            Toast.makeText(this, USER_ALREADY_EXISTS,  Toast.LENGTH_SHORT).show()
        } else {
            loginServices.saveUser(user, this, true)
            Toast.makeText(this, USER_REGISTERED_SUCCESSFULLY,  Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun setImageArray(array : ByteArray) {
        imgArr = array
    }

}
