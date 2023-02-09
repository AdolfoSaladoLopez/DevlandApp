package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.devlandapp.databinding.ActivityFeedBinding
import com.example.devlandapp.databinding.ActivityPerfilBinding
import com.example.devlandapp.databinding.MisProyectosActivityBinding

class PerfilActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var usuario = UsuarioData.usuario

        binding.nombreApellidosPerfil.text = "${usuario.nombre} ${usuario.apellidos}"
        binding.emailPerfil.text = usuario.email
        binding.descripcionPerfil.text = usuario.descripcion

        binding.editarPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfil::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.misProyectosPerfil.setOnClickListener {
            val intent = Intent(this, MisProyectosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.misProyectosInteresados.setOnClickListener {
            val intent = Intent(this, ProyectosInteresadosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}