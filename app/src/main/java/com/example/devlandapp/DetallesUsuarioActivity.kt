package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.UsuarioData.Companion.usuario
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesUsuarioBinding
import com.example.devlandapp.models.Notificacion
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
    var lista = Gestor.gestorNotificaciones.obtenerTodasNotificaciones()


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
                if (proyectoObtenido.usuariosSeleccionadosId.size < proyectoObtenido.numeroParticipantes!!) {
                    proyectoObtenido.usuariosSeleccionadosId.add(usuarioObtenido.id)
                    Gestor.gestorProyectos.modificarProyecto(proyectoObtenido)

                    binding.btnSeleccionar.text = "DESELECCIONAR USUARIOS"

                    val texto = "El usuario ${usuario.nombre} ${usuario.apellidos} te ha seleccionado para el proyecto ${proyectoObtenido.nombre}"
                    val notificacion = Notificacion(lista.size, texto, false,null,  usuarioObtenido.id,null, proyectoObtenido.id)
                    Gestor.gestorNotificaciones.registrarNotificacion(notificacion)
                }

            } else {
                proyectoObtenido.usuariosSeleccionadosId.remove(usuarioObtenido.id)
                Gestor.gestorProyectos.modificarProyecto(proyectoObtenido)

                binding.btnSeleccionar.text = "SELECCIONAR USUARIOS"

                val texto = "El usuario ${usuario.nombre} ${usuario.apellidos} te ha deseleccionado para el proyecto ${proyectoObtenido.nombre}"

                val notificacion = Notificacion(lista.size, texto, false,null,  usuarioObtenido.id,null, proyectoObtenido.id)
                Gestor.gestorNotificaciones.registrarNotificacion(notificacion)
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
        var proyecto = Proyecto()

        totalProyectos.forEach {
            if (it.id == idProyecto) {
                proyecto = it
            }
        }

        println("EL ID DEL PROYECTO ES: " + proyecto.id)
        return proyecto
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