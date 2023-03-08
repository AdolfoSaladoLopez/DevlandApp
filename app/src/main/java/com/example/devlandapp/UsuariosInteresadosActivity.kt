package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import com.example.devlandapp.adapters.UsuarioAdapter
import com.example.devlandapp.databinding.UsuariosInteresadosActivityBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class UsuariosInteresadosActivity : DrawerBaseActivity() {
    private lateinit var binding: UsuariosInteresadosActivityBinding

    private var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    private var usuariosInteresadosId: MutableList<Int> = mutableListOf()
    private var usuariosInteresados: MutableList<Usuario> = mutableListOf()
    private lateinit var lista: ListView
    private var proyecto: Proyecto = Proyecto()
    private var id: Int = 0

    init {
        totalUsuarios.addAll(UsuarioData.totalUsuarios)
        totalProyectos.addAll(UsuarioData.totalProyectos)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UsuariosInteresadosActivityBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)

        /* Recuperamos el ID del proyecto que queremos ver los usuarios interesados */
        id = intent.extras!!.getInt("proyecto")
        proyecto = obtenerProyectoId(id)

        /* Obtenemos los ids de los usuarios interesados */
        usuariosInteresadosId.clear()
        usuariosInteresadosId.addAll(proyecto.usuariosInteresadosId)

        /* Convertimos los ids en Usuarios */
        usuariosInteresados.addAll(obtenerUsuarios(usuariosInteresadosId))
        val intent2 = Intent(this, DetallesUsuarioActivity::class.java)

        /* Pasamos los proyectos al adapter */
        recarga()

        lista = findViewById(R.id.misProyectos)
        lista.setOnItemClickListener { _, _, position, _ ->
            val arrayList: ArrayList<Int> = arrayListOf()
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
        val listadoUsuariosEncontrados: MutableList<Usuario> = mutableListOf()

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