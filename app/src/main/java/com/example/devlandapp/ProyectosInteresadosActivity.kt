package com.example.devlandapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityProyectosInteresadosBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProyectosInteresadosActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityProyectosInteresadosBinding

    var usuario: Usuario = Usuario()
    private var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var proyectosInteresadosId: MutableList<Int> = mutableListOf()
    private var proyectosInteresados: MutableList<Proyecto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProyectosInteresadosBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)

        val intent2 = Intent(this, DetallesProyectoOtraPersonaActivity::class.java)

        var comprobante = true
        lifecycleScope.launch {
            while (comprobante) {
                totalProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
                delay(1000)

                if (totalProyectos[0].nombre != "") {

                    comprobante = false
                }
            }
            totalProyectos.clear()
            proyectosInteresados.clear()

            totalProyectos.addAll(UsuarioData.totalProyectos)
            usuario = UsuarioData.usuario
            proyectosInteresadosId = UsuarioData.usuario.proyectosInteresadosId

            proyectosInteresadosId.forEach {
                println("ESTOY DENTRO DE AQUÍ: " + it)
            }

            proyectosInteresados.addAll(obtenerProyectosInteresados())

           /* val lv1 = findViewById<ListView>(R.id.lista)
            lv1.setOnItemClickListener { _, _, position, _ ->

                intent2.putExtra("id", proyectosInteresados[position].id)
                startActivity(intent2)
            }*/

            proyectosInteresados.forEach {
                println("ESTOY DENTRO DE AQUÍ: " + it.nombre)
            }

            recarga()
        }

    }

    private fun recarga() {
        val lv1 = findViewById<ListView>(R.id.lista)

        val adapter = ProyectoAdapter(
            this,
            R.layout.activity_feed, proyectosInteresados
        )

        lv1.cacheColorHint = 0
        lv1.adapter = adapter
    }

    private fun obtenerProyectosInteresados(
    ): MutableList<Proyecto> {
        val listadoProyectosInteresados: MutableList<Proyecto> = mutableListOf()

        println(listadoProyectosInteresados)
        println(totalProyectos)

        totalProyectos.forEach { proyecto ->
            proyectosInteresadosId.forEach {
                if (proyecto.id == it) {
                    listadoProyectosInteresados.add(proyecto)
                }
            }
        }

        UsuarioData.usuario.proyectosInteresados?.addAll(listadoProyectosInteresados)
        println(listadoProyectosInteresados)

        return listadoProyectosInteresados
    }
}