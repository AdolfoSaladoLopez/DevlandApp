package com.example.devlandapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityFeedBinding
import com.example.devlandapp.models.Proyecto
import kotlinx.coroutines.*

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()
    private var proyectoAdapter: ProyectoAdapter? = null


    init {
        lifecycleScope.launch(Dispatchers.Main) {
            listadoProyectos = withContext(Dispatchers.IO) {
                Gestor.gestorProyectos.obtenerTodosProyectos()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listadoProyectos.forEach {
            println(it.nombre)
        }

        proyectoAdapter = ProyectoAdapter(this, R.layout.proyecto_concreto, listadoProyectos)
        this.recarga()
        proyectoAdapter = ProyectoAdapter(this, R.layout.proyecto_concreto, listadoProyectos)


    }

    private fun recarga() {

        val adapter = ProyectoAdapter(
            this,
            R.layout.activity_feed, listadoProyectos
        )

        val listView1 = findViewById<ListView>(R.id.lista)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }


}