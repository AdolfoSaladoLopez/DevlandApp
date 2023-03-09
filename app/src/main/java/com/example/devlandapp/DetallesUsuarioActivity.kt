package com.example.devlandapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import com.example.devlandapp.UsuarioData.Companion.usuario
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesUsuarioBinding
import com.example.devlandapp.models.Notificacion
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

@SuppressLint("SetTextI18n")
class DetallesUsuarioActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityDetallesUsuarioBinding
    private var idUsuario = 0
    private var idProyecto = 0
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var usuarioObtenido: Usuario = Usuario()
    private var proyectoObtenido: Proyecto = Proyecto()
    private var lista = Gestor.gestorNotificaciones.obtenerTodasNotificaciones()

    init {
        totalProyectos.addAll(UsuarioData.totalProyectos)
        totalUsuarios.addAll(UsuarioData.totalUsuarios)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recuperarIdPropiet()
        recuperarIntent()

        usuarioObtenido = obtenerUsuarioPorId()
        proyectoObtenido = obtenerProyectoPorId()

        binding.tvNombreApellidos.text = "${usuarioObtenido.nombre} ${usuarioObtenido.apellidos}"
        binding.tvCorreoElectronico.text = usuarioObtenido.email
        binding.tvDescripcionDetalle.text = usuarioObtenido.descripcion
        binding.ivUsuario.setImageResource(usuarioObtenido.imagen)

        if (saberSiUsuarioEstaSeleccionado(usuarioObtenido)) {
            binding.btnSeleccionar.text = "DESELECCIONAR USUARIOS"
        } else {
            binding.btnSeleccionar.text = "SELECCIONAR USUARIO"
        }

        binding.btnSeleccionar.setOnClickListener {
            if (!saberSiUsuarioEstaSeleccionado(usuarioObtenido)) {
                if (proyectoObtenido.usuariosSeleccionadosId.size < proyectoObtenido.numeroParticipantes!!) {
                    proyectoObtenido.usuariosSeleccionadosId.add(usuarioObtenido.id)
                    Gestor.gestorProyectos.modificarProyecto(proyectoObtenido)

                    binding.btnSeleccionar.text = "DESELECCIONAR USUARIOS"

                    lista = Gestor.gestorNotificaciones.obtenerTodasNotificaciones()


                    var id = if (lista.size == 0) {
                        0
                    } else {
                        lista[lista.size - 1].id + 1
                    }

                    val texto =
                        "El usuario ${usuario.nombre} ${usuario.apellidos} te ha seleccionado para el proyecto ${proyectoObtenido.nombre}"

                    val notificacion = Notificacion(
                        id,
                        texto,
                        false,
                        usuarioObtenido,
                        usuarioObtenido.id,
                        proyectoObtenido,
                        proyectoObtenido.id
                    )

                    Gestor.gestorNotificaciones.registrarNotificacion(notificacion)
                } else {
                    Toast.makeText(this, "No puede añadir más participantes.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                proyectoObtenido.usuariosSeleccionadosId.remove(usuarioObtenido.id)
                Gestor.gestorProyectos.modificarProyecto(proyectoObtenido)

                binding.btnSeleccionar.text = "SELECCIONAR USUARIOS"

                val texto =
                    "El usuario ${usuario.nombre} ${usuario.apellidos} te ha deseleccionado para el proyecto ${proyectoObtenido.nombre}"

                lista = Gestor.gestorNotificaciones.obtenerTodasNotificaciones()

                val id = if (lista.size == 0) {
                    0
                } else {
                    lista[lista.size - 1].id + 1
                }

                val notificacion = Notificacion(
                    id,
                    texto,
                    false,
                    usuarioObtenido,
                    usuarioObtenido.id,
                    proyectoObtenido,
                    proyectoObtenido.id
                )

                Gestor.gestorNotificaciones.registrarNotificacion(notificacion)
            }
        }
    }

    private fun recuperarIntent() {
        val hashMap: ArrayList<Int> = intent.extras!!.getIntegerArrayList("id") as ArrayList<Int>
        idUsuario = hashMap[0]
        idProyecto = hashMap[1]
    }

    private fun recuperarIdPropiet(){

        val idPropiet: Int = intent.extras!!.getInt("idpropiet")
        idUsuario = idPropiet
    }
    private fun obtenerUsuarioPorId(): Usuario {
        var usuario = Usuario()

        totalUsuarios.forEach {
            if (it.id == idUsuario) {
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