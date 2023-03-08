package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.devlandapp.databinding.ActivityPerfilBinding

class PerfilActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityPerfilBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val usuario = UsuarioData.usuario

        binding.nombreApellidosPerfil.text = "${usuario.nombre} ${usuario.apellidos}"
        binding.emailPerfil.text = usuario.email
        binding.descripcionPerfil.text = usuario.descripcion
        binding.imagenPerfil.setImageResource(usuario.imagen)

        binding.editarPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfil::class.java)
            startActivity(intent)
        }

        binding.misProyectosPerfil.setOnClickListener {
            val intent = Intent(this, MisProyectosActivity::class.java)
            startActivity(intent)
        }

        binding.misProyectosInteresados.setOnClickListener {
            val intent = Intent(this, ProyectosInteresadosActivity::class.java)
            startActivity(intent)
        }
    }
}