package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.databinding.MisProyectosActivityBinding
import com.example.devlandapp.models.Proyecto

class MisProyectosActivity : DrawerBaseActivity() {
    private lateinit var binding: MisProyectosActivityBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()
    private var listadoProyectosUsuario: MutableList<Proyecto> = mutableListOf()

    init {
        listadoProyectos = UsuarioData.totalProyectos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MisProyectosActivityBinding.inflate(layoutInflater)
        localizarTituloActivity("Mis proyectos")
        setContentView(binding.root)

        val intent = Intent(this, DetallesProyectoPropioActivity::class.java)

        listadoProyectos.forEach {
            if (it.idPropietario == UsuarioData.usuario.id) {
                listadoProyectosUsuario.add(it)
                UsuarioData.usuario.proyectosCreados?.add(it)
            }
        }

        rellenarUsuariosProyectos()

        recarga()

        val lv1 = findViewById<ListView>(R.id.misProyectos)
        lv1.setOnItemClickListener { parent, view, position, id ->
            intent.putExtra("id", listadoProyectosUsuario[position].id)
            startActivity(intent)
        }
    }

    private fun recarga() {
        val adapter = ProyectoAdapter(
            this,
            R.layout.mis_proyectos_activity, listadoProyectosUsuario.asReversed()
        )

        val listView1 = findViewById<ListView>(R.id.misProyectos)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }

    private fun rellenarUsuariosProyectos() {
        listadoProyectosUsuario.forEach { proyecto ->
            UsuarioData.totalUsuarios.forEach { usuario ->
                if (proyecto.idPropietario == usuario.id) {
                    proyecto.propietario = usuario
                }
            }
        }
    }
}