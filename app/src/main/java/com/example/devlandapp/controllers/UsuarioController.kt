package com.example.devlandapp.controllers

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.launch

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

    override fun obtenerUsuarioCorreoElectronico(correo: String): Usuario? {

        var usuario: Usuario? = Usuario()
        var listado: MutableList<Usuario> = mutableListOf()

        Db.conexion().collection("usuario")
            .whereEqualTo("email", correo)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    usuario = document.toObject(Usuario::class.java)
                    listado.add(usuario!!)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        listado.forEach {
            println("Dentro del foreach" + it.nombre)
        }

        return usuario
    }
}