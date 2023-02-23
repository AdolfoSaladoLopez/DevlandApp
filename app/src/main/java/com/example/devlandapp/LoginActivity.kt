package com.example.devlandapp

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnInicioSesion: Button? = null
    lateinit var tvOlvidarPwd: TextView
    lateinit var tvRegistro: TextView
    private var prefs: SharedPreferences? = null
    var usuario: Usuario? = Usuario()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        traerTodosUsuarios()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etContraseña)
        btnInicioSesion = findViewById(R.id.btnInicioSesion)
        tvOlvidarPwd = findViewById(R.id.tvOlvidarPwd)
        tvRegistro = findViewById(R.id.tvRegistro)

        prefs = getSharedPreferences("app", MODE_PRIVATE)

        establecerValoresSiExisten()

        tvOlvidarPwd.setOnClickListener {
            goToOlvidarPwd()
        }

        tvRegistro.setOnClickListener {
            goToRegistro()
        }

        btnInicioSesion?.setOnClickListener {
            val email = etEmail!!.text.toString()
            val password = etPassword!!.text.toString()

            if (login(email, password)) {

                totalUsuarios.forEach {
                    if (it.email.equals(email)) {
                        usuario = it

                        UsuarioData.usuario = usuario as Usuario
                    }
                }

                usuario = UsuarioData.usuario


                println("Contraseña usuario: ${usuario!!.password}. Contraseña texto: $password")

                if (usuario!!.password.equals(password)) {
                    goToFeed()
                } else {
                    Toast.makeText(this, "No ha sido posible acceder", Toast.LENGTH_SHORT).show()
                }

            }
            guardarPreferencias(email, password)
        }
    }

    private fun traerTodosUsuarios() {
        var comprobante = true

        totalUsuarios.clear()

        lifecycleScope.launch {
            while (comprobante) {
                totalUsuarios = Gestor.gestorUsuarios.obtenerTodosUsuarios()
                delay(1000)

                if (totalUsuarios.size > 0) {
                    comprobante = false
                }

                UsuarioData.ultimoIdUsuario = totalUsuarios.size
                Log.d(ContentValues.TAG, "Corriendo corrutina")
            }
        }

        UsuarioData.totalUsuarios = totalUsuarios
    }

    private fun comprobarCorreo(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun comprobarPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length > 7
    }

    private fun login(email: String, password: String): Boolean {
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
        } else {
            valido = true
        }
        return valido
    }

    private fun guardarPreferencias(email: String, password: String) {
        val editor = prefs!!.edit()

        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun establecerValoresSiExisten() {
        val email = prefs!!.getString("email", "")
        val password = prefs!!.getString("password", "")
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            etEmail!!.setText(email)
            etPassword!!.setText(password)
        }
    }

    private fun goToFeed() {
        val intent = Intent(this, FeedActivity::class.java)
        // Evita que pasemos de nuevo a la activity login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goToRegistro() {
        val intent = Intent(this, RegistrarUsuarioActivity::class.java)
        //Evita que pasemos de nuevo a la activity login
        startActivity(intent)
    }

    /* Método por implementar en futuras versiones */
    private fun goToOlvidarPwd() {
        //val intent = Intent(this, MainActivity::class.java)
        //Evita que pasemos de nuevo a la activity login
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // startActivity(intent)
    }
}