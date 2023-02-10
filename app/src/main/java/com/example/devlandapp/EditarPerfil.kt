package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityEditarPerfilBinding
import com.example.devlandapp.models.Usuario

class EditarPerfil : DrawerBaseActivity() {
    lateinit var binding: ActivityEditarPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var id = UsuarioData.usuario.id

        val textViewNombre = findViewById<TextView>(R.id.editarPerfil_etNombre)
        val textViewApellido = findViewById<TextView>(R.id.editarPerfil_etApellido)
        val textViewEmail = findViewById<TextView>(R.id.editarPerfil_email)
        val textViewDescripcion = findViewById<TextView>(R.id.multiline)
        val textPassword = findViewById<TextView>(R.id.editarPerfil_password)
        val textPasswordrep = findViewById<TextView>(R.id.editarPerfil_passwordrep)

        textViewNombre.text = UsuarioData.usuario.nombre
        textViewApellido.text = UsuarioData.usuario.apellidos
        textViewEmail.text = UsuarioData.usuario.email
        textViewDescripcion.text = UsuarioData.usuario.descripcion
        textPassword.text = UsuarioData.usuario.password
        textPasswordrep.text = UsuarioData.usuario.password

        val boton1 = findViewById<Button>(R.id.editarPerfil_register)

        var usuario: Usuario = Usuario()

        boton1.setOnClickListener {
            val nombre = textViewNombre.text!!.toString()
            val apellido = textViewApellido.text!!.toString()
            val email = textViewEmail.text!!.toString()
            val descripcion = textViewDescripcion.text!!.toString()
            val contraseña = textPassword.text!!.toString()
            val contraseñarep = textPassword.text!!.toString()

            if (contraseña.equals(contraseñarep)) {
                usuario = Usuario(
                    id,
                    nombre,
                    apellido,
                    email,
                    contraseña,
                    UsuarioData.usuario.administrador,
                    descripcion,
                    UsuarioData.usuario.imagen,
                    UsuarioData.usuario.proyectosCreados,
                    UsuarioData.usuario.proyectosInteresados,
                    UsuarioData.usuario.proyectosCreadosId,
                    UsuarioData.usuario.proyectosInteresadosId
                )

                if (Gestor.gestorUsuarios.modificarUsuario(usuario)) {
                    Toast.makeText(this, "El usuario ha sido modificado", Toast.LENGTH_SHORT).show()
                    UsuarioData.usuario = usuario

                    val intent = Intent(this, PerfilActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "El usuario no ha sido modificado", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}