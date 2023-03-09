package com.example.devlandapp

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesOtroUsuarioBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

@SuppressLint("SetTextI18n")
class PerfilOtroUsuarioActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityDetallesOtroUsuarioBinding
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
        binding = ActivityDetallesOtroUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recuperarIdPropiet()
        recuperarIntent()

        usuarioObtenido = obtenerUsuarioPorId()
        proyectoObtenido = obtenerProyectoPorId()

        binding.tvNombreApellidos.text = "${usuarioObtenido.nombre} ${usuarioObtenido.apellidos}"
        binding.tvCorreoElectronico.text = usuarioObtenido.email
        binding.tvDescripcionDetalle.text = usuarioObtenido.descripcion
        binding.ivUsuario.setImageResource(usuarioObtenido.imagen)
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
}