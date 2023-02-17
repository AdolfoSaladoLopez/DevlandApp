package com.example.devlandapp

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityCrearProyectoBinding
import com.example.devlandapp.databinding.ActivityRegistrarUsuarioBinding
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrarUsuarioActivity : AppCompatActivity() {
    private var etNombre: EditText? = null
    private var etApellido: EditText? = null
    private var regis_Contraseña: EditText? = null
    private var reg_password: EditText? = null
    private var reg_email: EditText? = null
    private var reg_confirmemail: EditText? = null
    private var re_register: Button? = null
    private var prefs: SharedPreferences? = null
    private var usuario: Usuario = Usuario()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()

    lateinit var binding: ActivityRegistrarUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Log.w("ELID", "${UsuarioData.ultimoIdUsuario}")

        etNombre = binding.etNombreRegistro
        etApellido = binding.etApellido
        regis_Contraseña = binding.regisPassword
        reg_password = binding.regPassword
        reg_email = binding.regEmail
        re_register = binding.regRegister

        re_register?.setOnClickListener {

            var nombre = etNombre!!.text.toString()
            var apellido = etApellido!!.text.toString()
            var contraseña = regis_Contraseña!!.text.toString()
            var repPasword = reg_password!!.text.toString()
            var email = reg_email!!.text.toString()

            if (registro(nombre, apellido, contraseña, repPasword, email)) {

                usuario.id = UsuarioData.ultimoIdUsuario
                usuario.nombre = nombre
                usuario.apellidos = apellido
                usuario.email = email
                usuario.password = contraseña
                usuario.imagen = R.drawable.person
                usuario.administrador = false

                if (Gestor.gestorUsuarios.registrarUsuario(usuario)) {
                    Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                    UsuarioData.usuario = usuario
                    goToFeed()
                } else {
                    Toast.makeText(this, "Usuario no registrado con éxito", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun registro(
        nombre: String,
        apellido: String,
        password: String,
        reppassword: String,
        email: String,
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


    private fun goToFeed() {
        val intent = Intent(this, FeedActivity::class.java)
        // Evita que pasemos de nuevo a la activity login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}