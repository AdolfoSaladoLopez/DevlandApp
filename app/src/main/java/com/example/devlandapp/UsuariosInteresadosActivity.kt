package com.example.devlandapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.adapters.ProyectosInteresadosAdapter
import com.example.devlandapp.adapters.UsuarioAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityProyectosInteresadosBinding
import com.example.devlandapp.databinding.UsuariosInteresadosActivityBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UsuariosInteresadosActivity : DrawerBaseActivity() {
    private lateinit var binding: UsuariosInteresadosActivityBinding

    private var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    private var usuariosInteresadosId: MutableList<Int> = mutableListOf()
    private var usuariosInteresados: MutableList<Usuario> = mutableListOf()
    private lateinit var lista: ListView
    private var proyecto: Proyecto = Proyecto()
    private var id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UsuariosInteresadosActivityBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)

        /* Obtenemos todos los proyectos */
        totalProyectos.clear()
        totalProyectos.addAll(UsuarioData.totalProyectos)

        /* Obtenemos todos los usuarios */
        totalUsuarios.clear()
        totalUsuarios.addAll(UsuarioData.totalUsuarios)
        println("TAMAÑO: " + totalUsuarios.size)

        /* Recuperamos el ID del proyecto que queremos ver los usuarios interesados */
        id = intent.extras!!.getInt("proyecto")
        println(id)
        proyecto = obtenerProyectoId(id)

        /* Obtenemos los ids de los usuarios interesados */
        usuariosInteresadosId.clear()
        usuariosInteresadosId.addAll(proyecto.usuariosInteresadosId)

        println("TAMAÑO PROYECTO: " + proyecto.usuariosInteresadosId.size)

        /* Convertimos los ids en Usuarios */
        usuariosInteresados.addAll(obtenerUsuarios(usuariosInteresadosId))
        val intent2 = Intent(this, DetallesUsuarioActivity::class.java)

        println("TAMAÑO USUARIOS PROYECTO: " + usuariosInteresados.size)

        /* Pasamos los proyectos al adapter */
        recarga()


        lista = findViewById(R.id.misProyectos)
        lista.setOnItemClickListener { _, _, position, _ ->
           var arrayList:ArrayList<Int> = arrayListOf()
            arrayList.add(usuariosInteresados[position].id)
            arrayList.add(proyecto.id)
            intent2.putExtra("id", arrayList)
            startActivity(intent2)
        }
    }

    private fun recarga() {

        val lista = findViewById<ListView>(R.id.misProyectos)

        val adapter = UsuarioAdapter(
            this,
            R.layout.activity_feed, usuariosInteresados
        )

        lista.cacheColorHint = 0
        lista.adapter = adapter
    }

    private fun obtenerProyectoId(id: Int): Proyecto {
        var proyectoEncontrado = Proyecto()
        totalProyectos.forEach { proyecto ->
            if (proyecto.id == id) {
                proyectoEncontrado = proyecto
            }
        }
        return proyectoEncontrado
    }

    private fun obtenerUsuarios(listadoUsuariosId: MutableList<Int>): MutableList<Usuario> {
        var listadoUsuariosEncontrados: MutableList<Usuario> = mutableListOf()

        totalUsuarios.forEach { usuario ->
            listadoUsuariosId.forEach { id ->
                if (usuario.id == id) {
                    listadoUsuariosEncontrados.add(usuario)
                }
            }
        }

        return listadoUsuariosEncontrados
    }
}