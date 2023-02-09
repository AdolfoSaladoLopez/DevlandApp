package com.example.devlandapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityFeedBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
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

        val intent = Intent(this, DetallesProyectoPropioActivity::class.java)
        val intent2 = Intent(this, DetallesProyectoOtraPersonaActivity::class.java)

        lifecycleScope.launch {
            while (comprobante) {
                listadoProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
                delay(1000)

                if (listadoProyectos[0].nombre != "") {
                    comprobante = false
                }

                UsuarioData.ultimoId = listadoProyectos.size
                Log.d(TAG, "Corriendo corrutina")
            }

            UsuarioData.totalProyectos.addAll(listadoProyectos)
            recarga()

            val lv1 = findViewById<ListView>(R.id.lista)
            lv1.setOnItemClickListener { _, _, position, _ ->

                if (listadoProyectos[position].idPropietario == UsuarioData.usuario.id) {
                    intent.putExtra("id", listadoProyectos[position].id)
                    startActivity(intent)
                } else {
                    intent2.putExtra("id", listadoProyectos[position].id)
                    startActivity(intent2)
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