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

class RegisterActivity : AppCompatActivity() {

    private lateinit var loginServices : LoginServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginServices= LoginServices(this)
    }

    fun xxx(view : View) {
        Toast.makeText(this, "xxxx.",  Toast.LENGTH_SHORT).show()
    }

    fun createProfile(view : View) {
        val name = findViewById<TextView>(R.id.nombre).text.toString()
        val email = findViewById<TextView>(R.id.correo).text.toString()
        val password = findViewById<TextView>(R.id.contraseña).text.toString()
        val age = findViewById<TextView>(R.id.edad).text.toString().toInt()

        val user = User(null, name, email, age, password)
        val exists = this.loginServices.existsUser(user)
        if (exists) {
            Toast.makeText(this, "El usuario ya existe.",  Toast.LENGTH_SHORT).show()
        } else {
            loginServices.saveUser(user, this, true)
            Toast.makeText(this, "Se creo exitosamente el usuario.",  Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun acceder(view: View)
    {
        val email = findViewById<TextView>(R.id.email);
        val password = findViewById<TextView>(R.id.password);
    }
}
