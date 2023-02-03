package com.example.devlandapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.MisProyectosActivityBinding
import com.example.devlandapp.models.Proyecto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MisProyectosActivity : DrawerBaseActivity() {
    private lateinit var binding: MisProyectosActivityBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()
    private var listadoProyectosUsuario: MutableList<Proyecto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MisProyectosActivityBinding.inflate(layoutInflater)
        localizarTituloActivity("Mis proyectos")
        setContentView(binding.root)
        var comprobante = true

        val intent = Intent(this, DetallesProyectoPropioActivity::class.java)


        lifecycleScope.launch {
            while (comprobante) {
                listadoProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
                delay(1000)

                if (listadoProyectos[0].nombre != "") {
                    comprobante = false
                }
                Log.d(ContentValues.TAG, "Corriendo corrutina")
            }

            listadoProyectos.forEach {
                if (it.idPropietario == UsuarioData.usuario.id) {
                    listadoProyectosUsuario.add(it)
                    UsuarioData.usuario.proyectosCreados?.add(it)
                }
            }

            recarga()

        }


        val lv1 = findViewById<ListView>(R.id.misProyectos)
        lv1.setOnItemClickListener { parent, view, position, id ->
            println("Estoy en la vista: " + listadoProyectosUsuario[position].id)
            intent.putExtra("id", listadoProyectosUsuario[position].id)
            startActivity(intent)
        }
    }

    fun recarga() {
        val adapter = ProyectoAdapter(
            this,
            R.layout.mis_proyectos_activity, listadoProyectosUsuario
        )

        val listView1 = findViewById<ListView>(R.id.misProyectos)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }

}