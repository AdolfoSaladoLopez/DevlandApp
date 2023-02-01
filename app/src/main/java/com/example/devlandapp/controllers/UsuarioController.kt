package com.example.devlandapp.controllers

import com.example.devlandapp.models.Usuario

class UsuarioController : UsuarioDAO {
    override fun obtenerTodosUsuarios(): MutableList<Usuario> {
        val listadoTotalUsuarios: MutableList<Usuario> = mutableListOf()

        Db.conexion().collection("usuario")
            .get()
            .addOnSuccessListener {
                for (usuario in it) {
                    listadoTotalUsuarios.add((usuario.toObject(Usuario::class.java)))
                }
            }

        return listadoTotalUsuarios
    }

    override fun obtenerUsuarioId(id: Int): Usuario {
        var usuario: Usuario = Usuario()

        Db.conexion().collection("usuario")
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                usuario = it.toObject(Usuario::class.java)!!
            }

        return usuario
    }

    override fun registrarUsuario(usuario: Usuario): Boolean {
        Db.conexion().collection("usuario")
            .document(usuario.id.toString())
            .set(usuario)

        return true
    }

    override fun modificarUsuario(usuario: Usuario): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("usuario")
            .document(usuario.id.toString())
            .set(usuario)

        if (usuario.id?.let { obtenerUsuarioId(it) } != null) {
            validador = true
        }

        return validador
    }

    override fun eliminarUsuario(usuario: Usuario): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("usuario")
            .document(usuario.id.toString())
            .delete()
            .addOnSuccessListener { validador = true }

        return validador
    }
}