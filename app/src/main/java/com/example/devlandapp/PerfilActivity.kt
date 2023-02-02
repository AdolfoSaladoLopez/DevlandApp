package com.example.devlandapp

import android.os.Bundle
import android.widget.TextView

class PerfilActivity : DrawerBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localizarTituloActivity("Perfil")
        setContentView(R.layout.activity_perfil)

        val textViewNombre = findViewById<TextView>(R.id.nombre_y_apellidos)
        val textViewEmail = findViewById<TextView>(R.id.email)
        val textViewDescripcion = findViewById<TextView>(R.id.descripcion)

        var usuario = UsuarioData.usuario

        textViewNombre.text = usuario.nombre
        textViewEmail.text = usuario.email
        textViewDescripcion.text = usuario.descripcion
    }
}