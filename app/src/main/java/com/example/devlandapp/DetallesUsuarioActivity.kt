package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesUsuarioBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@SuppressLint("SetTextI18n")
class DetallesUsuarioActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityDetallesUsuarioBinding
    private var idUsuario = 0
    private var idProyecto = 0
    var totalUsuarios: MutableList<Usuario> = mutableListOf()
    var totalProyectos: MutableList<Proyecto> = mutableListOf()
    var usuarioObtenido: Usuario = Usuario()
    var proyectoObtenido: Proyecto = Proyecto()


    init {
        totalProyectos.addAll(UsuarioData.totalProyectos)
        totalUsuarios.addAll(UsuarioData.totalUsuarios)
        usuarioObtenido = obtenerUsuarioPorId()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recuperarIntent()

        proyectoObtenido = obtenerProyectoPorId()

        binding.tvNombreApellidos.text = "${usuarioObtenido.nombre} ${usuarioObtenido.apellidos}"
        binding.tvCorreoElectronico.text = usuarioObtenido.email
        binding.tvDescripcionDetalle.text = usuarioObtenido.descripcion

        binding.btnSeleccionar.setOnClickListener {

            if (!saberSiUsuarioEstaSeleccionado(usuarioObtenido)) {
                proyectoObtenido.usuariosSeleccionadosId.add(usuarioObtenido.id)
                Gestor.gestorProyectos.modificarProyecto(proyectoObtenido)

                binding.btnSeleccionar.text = "DESELECCIONAR USUARIOS"
            } else {
                proyectoObtenido.usuariosSeleccionadosId.remove(usuarioObtenido.id)
                Gestor.gestorProyectos.modificarProyecto(proyectoObtenido)

                binding.btnSeleccionar.text = "SELECCIONAR USUARIOS"
            }
        }
    }

    private fun recuperarIntent() {
        val hashMap: ArrayList<Int> = intent.extras!!.getIntegerArrayList("id") as ArrayList<Int>
        idUsuario = hashMap[0]
        idProyecto = hashMap[1]
    }

    private fun obtenerUsuarioPorId(): Usuario {
        var usuario = Usuario()

        totalUsuarios.forEach {
            if (usuario.id == idUsuario) {
                usuario = it
            }
        }
        return usuario
    }

    private fun obtenerProyectoPorId(): Proyecto {
        var proyecto: Proyecto = Proyecto()

        totalProyectos.forEach {
            if (it.id == idProyecto) {
                proyecto = it
            }
        }

        println("EL ID DEL PROYECTO ES: " + proyecto.id)
        return proyecto
    }

    private fun traerTodosUsuarios() {
        var comprobante = true

        totalUsuarios.clear()

        lifecycleScope.launch {
            while (comprobante) {
                totalUsuarios = Gestor.gestorUsuarios.obtenerTodosUsuarios()
                delay(1000)

                if (totalUsuarios.size > 0) {
                    comprobante = false
                }
            }
        }

        UsuarioData.totalUsuarios.clear()
        UsuarioData.totalUsuarios.addAll(totalUsuarios)
    }

    private fun traerTodosProyectos() {


        var comprobante = true

        totalProyectos.clear()

        lifecycleScope.launch {
            while (comprobante) {
                totalProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
                delay(1000)

                if (totalProyectos[0].nombre != "") {

                    comprobante = false
                }

                UsuarioData.ultimoId = totalProyectos.size
                Log.d(ContentValues.TAG, "Corriendo corrutina")
            }

            UsuarioData.totalProyectos.clear()
            UsuarioData.totalProyectos.addAll(totalProyectos)
        }
    }

    private fun saberSiUsuarioEstaSeleccionado(usuario: Usuario): Boolean {
        var estaSeleccionado = false

        proyectoObtenido.usuariosSeleccionadosId.forEach {
            if (it == usuario.id) {
                estaSeleccionado = true
            }
        }

        return estaSeleccionado
    }
}