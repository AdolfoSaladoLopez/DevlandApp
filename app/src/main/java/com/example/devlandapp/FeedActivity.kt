package com.example.devlandapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityFeedBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityFeedBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    private lateinit var myAdapter: ProyectoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)

        traerTodosUsuarios()

        val intent = Intent(this, DetallesProyectoPropioActivity::class.java)
        val intent2 = Intent(this, DetallesProyectoOtraPersonaActivity::class.java)

        obtenerTotalProyectos(intent, intent2)
    }

    private fun obtenerTotalProyectos(intent: Intent, intent2: Intent) {
        var comprobante = true
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
            UsuarioData.totalProyectos.clear()
            UsuarioData.totalProyectos.addAll(listadoProyectos)

            recarga()

            rellenarUsuariosProyectos()


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

    private fun traerTodosUsuarios() {
        var comprobante = true

        lifecycleScope.launch {
            while (comprobante) {
                totalUsuarios = Gestor.gestorUsuarios.obtenerTodosUsuarios()
                delay(1000)

                if (totalUsuarios.size > 0) {
                    comprobante = false
                }

                UsuarioData.ultimoIdUsuario = totalUsuarios.size
            }
        }

        UsuarioData.totalUsuarios = totalUsuarios
    }

    private fun rellenarUsuariosProyectos() {
        listadoProyectos.forEach { proyecto ->
            totalUsuarios.forEach { usuario ->
                if (proyecto.idPropietario == usuario.id) {
                    proyecto.propietario = usuario
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