package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import com.example.devlandapp.adapters.UsuarioAdapter
import com.example.devlandapp.databinding.UsuariosInteresadosActivityBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class UsuariosSeleccionadosActivity : DrawerBaseActivity() {
    private lateinit var binding: UsuariosInteresadosActivityBinding
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    private var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var totalUsuariosSeleccionadosId: MutableList<Int> = mutableListOf()
    private var totalUsuariosSeleccionados: MutableList<Usuario> = mutableListOf()
    private var idProyecto: Int = 0
    private var proyectoObtenido: Proyecto = Proyecto()

    init {
        totalUsuarios.addAll(UsuarioData.totalUsuarios)
        totalProyectos.addAll(UsuarioData.totalProyectos)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UsuariosInteresadosActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recuperarIntent()

        proyectoObtenido = obtenerProyectoConcreto()

        totalUsuariosSeleccionadosId.addAll(proyectoObtenido.usuariosSeleccionadosId)

        totalUsuariosSeleccionados.addAll(obtenerUsuariosSeleccionados(totalUsuariosSeleccionadosId))

        recarga()

        val intent = Intent(this, DetallesUsuarioActivity::class.java)
        val lista = findViewById<ListView>(R.id.misProyectos)
        lista.setOnItemClickListener { _, _, position, _ ->
            val arrayList: ArrayList<Int> = arrayListOf()
            arrayList.add(totalUsuariosSeleccionados[position].id)
            arrayList.add(proyectoObtenido.id)
            intent.putExtra("id", arrayList)
            startActivity(intent)
        }
    }

    private fun recuperarIntent() {
        idProyecto = intent.extras!!.getInt("idProyecto")
    }

    private fun obtenerProyectoConcreto(): Proyecto {
        var proyecto = Proyecto()

        totalProyectos.forEach {
            if (it.id == idProyecto) {
                proyecto = it
            }
        }
        return proyecto
    }

    private fun obtenerUsuariosSeleccionados(idUsuarios: MutableList<Int>): MutableList<Usuario> {
        val listadoUsuarios: MutableList<Usuario> = mutableListOf()

        idUsuarios.forEach { idUsuario ->
            totalUsuarios.forEach { usuario ->
                if (idUsuario == usuario.id) {
                    listadoUsuarios.add(usuario)
                }
            }
        }

        return listadoUsuarios
    }

    private fun recarga() {
        val lista = findViewById<ListView>(R.id.misProyectos)

        val adapter = UsuarioAdapter(
            this,
            R.layout.activity_feed, totalUsuariosSeleccionados
        )

        lista.cacheColorHint = 0
        lista.adapter = adapter
    }
}