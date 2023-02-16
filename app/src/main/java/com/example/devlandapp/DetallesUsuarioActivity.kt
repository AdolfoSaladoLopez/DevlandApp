package com.example.devlandapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesUsuarioBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

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
                } else {
                    Toast.makeText(this, "No puede añadir más participantes.", Toast.LENGTH_SHORT)
                        .show()
                }

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