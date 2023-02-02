package com.example.devlandapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var comprobante = true

        lifecycleScope.launch {
            while (comprobante) {
                listadoProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
                delay(1000)

                if (listadoProyectos[0].nombre != "") {
                    comprobante = false
                }
                Log.d(TAG, "Corriendo corrutina")
            }

            recarga()

        }

    }

    fun recarga() {

        val adapter = ProyectoAdapter(
            this,
            R.layout.activity_feed, listadoProyectos
        )

        val listView1 = findViewById<ListView>(R.id.lista)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }

    private fun obtenerProyectos() {
        CoroutineScope(Dispatchers.IO).launch {
            listadoProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
            runOnUiThread {
                listadoProyectos.forEach {
                    println(it.nombre)
                }
            }
        }
    }


}