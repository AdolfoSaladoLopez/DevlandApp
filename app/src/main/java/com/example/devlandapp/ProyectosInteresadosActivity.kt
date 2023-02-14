package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.databinding.ActivityProyectosInteresadosBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class ProyectosInteresadosActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityProyectosInteresadosBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProyectosInteresadosBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)
        var comprobante = true

        val intent2 = Intent(this, DetallesProyectoOtraPersonaActivity::class.java)
        var totalProyectos: MutableList<Proyecto> = UsuarioData.totalProyectos
        var usuario: Usuario = UsuarioData.usuario
        var proyectosInteresadosId: MutableList<Int> = usuario.proyectosInteresadosId

        proyectosInteresados(totalProyectos, proyectosInteresadosId)

        recarga()

        val lv1 = findViewById<ListView>(R.id.lista)
        lv1.setOnItemClickListener { _, _, position, _ ->

            intent2.putExtra("id", listadoProyectos[position].id)
            startActivity(intent2)
        }
    }

    private fun proyectosInteresados(
        totalProyectos: MutableList<Proyecto>,
        proyectosInteresadosId: MutableList<Int>
    ) {
        totalProyectos.forEach { proyecto ->
            proyectosInteresadosId.forEach {
                if (proyecto.id == it) {
                    listadoProyectos.add(proyecto)
                }
            }
        }
    }

    private fun recarga() {
        listadoProyectos = listadoProyectos.asReversed()

        val adapter = ProyectoAdapter(
            this,
            R.layout.activity_feed, listadoProyectos
        )

        val listView1 = findViewById<ListView>(R.id.lista)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }
}