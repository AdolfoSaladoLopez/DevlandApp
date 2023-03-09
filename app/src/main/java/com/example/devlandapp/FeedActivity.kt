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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityFeedBinding
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    private lateinit var myAdapter: ProyectoAdapter

    private var ubicacionElegida: String? = "-"
    private var modoTrabajoElegido: String? = "-"
    private var tecnologiaElegido: String? = "-"
    private var idiomaElegido: String? = "-"
    private var verProyectosLLenos: Boolean? = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        traerTodosUsuarios()

        ubicacionElegida = intent.extras?.getString("ubicacion")
        modoTrabajoElegido = intent.extras?.getString("modo")
        tecnologiaElegido = intent.extras?.getString("tecnologia")
        idiomaElegido = intent.extras?.getString("idioma")
        verProyectosLLenos = intent.extras?.getBoolean("verProyectosLLenos")

        if (ubicacionElegida == null) {
            ubicacionElegida = "-"
        }
        if (modoTrabajoElegido == null) {
            modoTrabajoElegido = "-"
        }
        if (tecnologiaElegido == null) {
            tecnologiaElegido = "-"
        }
        if (idiomaElegido == null) {
            idiomaElegido = "-"
        }
        if (verProyectosLLenos == null) {
            verProyectosLLenos = true
        }

        println("ubicacionElegida: $ubicacionElegida")
        println("modoTrabajoElegido: $modoTrabajoElegido")
        println("tecnologiaElegido: $tecnologiaElegido")
        println("idiomaElegido: $idiomaElegido")
        println("verProyectosLLenos: $verProyectosLLenos")


        val intent = Intent(this, DetallesProyectoPropioActivity::class.java)
        val intent2 = Intent(this, DetallesProyectoOtraPersonaActivity::class.java)

        obtenerTotalProyectos(intent, intent2)

        fab.setOnClickListener {
            val intentFiltrado = Intent(this, FiltradoActivity::class.java)
            intentFiltrado.putExtra("ubicacion", ubicacionElegida)
            intentFiltrado.putExtra("modoTrabajo", modoTrabajoElegido)
            intentFiltrado.putExtra("tecnologia", tecnologiaElegido)
            intentFiltrado.putExtra("idioma", idiomaElegido)
            intentFiltrado.putExtra("verProyectosLLenos", verProyectosLLenos)
            startActivity(intentFiltrado)
        }
    }

    private fun obtenerTotalProyectos(intent: Intent, intent2: Intent) {
        var comprobante = true
        var contador = 0

        lifecycleScope.launch {
            while (comprobante) {
                listadoProyectos.clear()
                listadoProyectos = Gestor.gestorProyectos.obtenerProyectosFiltrados(
                    ubicacionElegida,
                    modoTrabajoElegido,
                    tecnologiaElegido,
                    idiomaElegido,
                    verProyectosLLenos
                )
                delay(1000)

                if (listadoProyectos.size > 0) {
                    comprobante = false
                    UsuarioData.ultimoId = listadoProyectos.get((listadoProyectos.size - 1)).id + 1

                }
                contador++

                if (contador == 10) {
                    comprobante = false
                    Toast.makeText(
                        this@FeedActivity,
                        "No hay proyectos que cumplan los filtros",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Log.d(TAG, "Corriendo corrutina")
            }

            println("listadoProyectos: $listadoProyectos")

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
