package com.example.devlandapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityFeedBinding
import com.example.devlandapp.models.Proyecto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityFeedBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
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

            val lv1 = findViewById<ListView>(R.id.lista)
            lv1.setOnItemClickListener { _, _, position, _ ->
                if (listadoProyectos[position].nombre == "País Vasco") {
                    Toast.makeText(
                        applicationContext, "Soy del " + listadoProyectos[position].nombre,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext, "Soy de " + listadoProyectos[position].nombre,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

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