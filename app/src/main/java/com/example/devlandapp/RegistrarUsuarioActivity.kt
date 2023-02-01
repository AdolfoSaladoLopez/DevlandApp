package com.example.devlandapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.devlandapp.models.Usuario

class RegistrarUsuarioActivity : AppCompatActivity() {
    private var etNombre: EditText? = null
    private var etApellido: EditText? = null
    private var regis_Contraseña: EditText? = null
    private var reg_password: EditText? = null
    private var reg_email: EditText? = null
    private var reg_confirmemail: EditText? = null
    private var etDescripcion: EditText? = null
    private var re_register: Button? = null
    private var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        regis_Contraseña = findViewById(R.id.regis_password)
        reg_password = findViewById(R.id.reg_password)
        reg_email = findViewById(R.id.reg_email)
        reg_confirmemail = findViewById(R.id.reg_confirmemail)
        etDescripcion = findViewById(R.id.etDescripcion)
        re_register = findViewById(R.id.reg_register)

        re_register?.setOnClickListener {

            val nombre = etNombre!!.text.toString()
            val apellido = etApellido!!.text.toString()
            val contraseña = regis_Contraseña!!.text.toString()
            val repPasword = reg_password!!.text.toString()
            val email = reg_email!!.text.toString()
            val conEmail = reg_confirmemail!!.text.toString()
            val descripcion = etDescripcion!!.text.toString()

            if (registro(nombre, apellido, contraseña, repPasword, email, conEmail, descripcion)) {

                val usuario: Usuario
                //TODO: Revisar esto que tiene pinta de fallar por todas partes
                Usuario(
                    4,
                    nombre,
                    apellido,
                    email,
                    contraseña,
                    false,
                    descripcion,
                    0,
                    null,
                    null,
                    null
                )

                goToFeed()
            }
        }
    }

    private fun registro(
        nombre: String,
        apellido: String,
        password: String,
        reppassword: String,
        email: String,
        correorep: String,
        descripcion: String
    ): Boolean {
        var valido = false
        if (!comprobarCorreo(email)) {
            Toast.makeText(
                this,
                "El correo electrónico introducido, no es válido. Introduce un e-mail correcto.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!comprobarPassword(password)) {
            Toast.makeText(
                this,
                "Contraseña no válida. Ingresa al menos una contraseña de 8 caracteres.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!comprobarNombre(nombre)) {
            Toast.makeText(this, "Nombre no valido", Toast.LENGTH_SHORT).show()
        } else if (!comprobarApellido(apellido)) {
            Toast.makeText(this, "Apellido no valido", Toast.LENGTH_SHORT).show()
        } else if (!comprobarRepPassword(reppassword, password)) {
            Toast.makeText(this, "la contraseña no coinciden", Toast.LENGTH_SHORT).show()
        } else if (!comprobarCorreorep(correorep, email)) {
            Toast.makeText(this, "Los correos no coinciden", Toast.LENGTH_SHORT).show()
        } else if (!comprobarDescripcion(descripcion)) {
            Toast.makeText(this, "Descripcion vacio", Toast.LENGTH_SHORT).show()
        } else {
            valido = true
        }
        return valido
    }

    private fun comprobarNombre(nombre: String): Boolean {
        return !TextUtils.isEmpty(nombre)
    }

    private fun comprobarApellido(apellido: String): Boolean {
        return !TextUtils.isEmpty(apellido)
    }

    private fun comprobarPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length > 7
    }

    private fun comprobarRepPassword(reppassword: String, password: String): Boolean {
        return !TextUtils.isEmpty(reppassword) && reppassword == password
    }

    private fun comprobarCorreo(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun comprobarCorreorep(correorep: String, email: String): Boolean {
        return !TextUtils.isEmpty(correorep) && correorep == email
    }

    private fun comprobarDescripcion(descripcion: String): Boolean {
        return !TextUtils.isEmpty(descripcion)
    }


    private fun goToFeed() {
        val intent = Intent(this, FeedActivity::class.java)
        // Evita que pasemos de nuevo a la activity login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}