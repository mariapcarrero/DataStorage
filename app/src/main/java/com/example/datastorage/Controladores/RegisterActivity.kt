package com.example.datastorage.Controladores

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.provider.MediaStore
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import com.example.datastorage.R
import com.example.datastorage.Servicios.LoginServices
import com.example.datastorage.Modelos.User
import com.karumi.dexter.Dexter
import android.Manifest
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import android.graphics.Bitmap

class RegisterActivity : AppCompatActivity() {

    private lateinit var loginServices : LoginServices
    private lateinit var imgArr : ByteArray
    private val GALLERY : Int = 1889

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginServices= LoginServices(this)

        requestMultiplePermisions()
    }

    fun requestMultiplePermisions() {
        val context = this
        Dexter.withActivity(this).
            withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(context, "Ya se aceptaron todos los permisos!",  Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Se nego alguno de los permisos!",  Toast.LENGTH_SHORT).show()
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

    fun uploadPhoto(view : View?) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Select action")
        dialog.setItems(arrayOf("Select photo from gallery"), DialogInterface.OnClickListener { dialog, which ->
            if (which == 0) {
                chosePhotoFromGallery()
            }
        })
        dialog.show()
    }

    fun chosePhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_CANCELED) {
            return
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                val uri = data.data

                var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                imgArr = stream.toByteArray()

                findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
            }
        }
    }

    fun createProfile(view : View) {
        val name = findViewById<TextView>(R.id.nombre).text.toString()
        val email = findViewById<TextView>(R.id.correo).text.toString()
        val password = findViewById<TextView>(R.id.contraseña).text.toString()
        val age = findViewById<TextView>(R.id.edad).text.toString().toInt()

        val user = User(null, name, email, age, password, imgArr)
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
