package com.example.devlandapp.controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.devlandapp.models.Proyecto
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
                println("Usuario con ID" + usuario.id + " . Nombre: " + usuario.nombre)

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

        return usuario
    }

    override fun obtenerProyectosInteresados(usuario: Usuario?): MutableList<Proyecto> {
        var listadoProyectosInteresadosId: MutableList<Int>? = usuario?.proyectosInteresadosId
        var listadoProyectosInteresados: MutableList<Proyecto> = mutableListOf()

        listadoProyectosInteresadosId?.forEach {
            listadoProyectosInteresados.add(Gestor.gestorProyectos.obtenerProyectoId(it))
        }

        return listadoProyectosInteresados
    }

    override fun obtenerProyectosInteresadosId(usuario: Usuario): MutableList<Int>? {
        var listado: MutableList<Int> = mutableListOf()

        Db.conexion().collection("usuario")
            .document(usuario.id.toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var usuario = document.toObject(Usuario::class.java)!!

                    listado.addAll(usuario.proyectosInteresadosId)
                }
            }

        return listado
    }

    override fun obtenerProyectosCreados(usuario: Usuario?): MutableList<Proyecto>? {
        var listadoProyectosCreadosId: MutableList<Int>? = usuario?.proyectosInteresadosId
        var listadoProyectosCreados: MutableList<Proyecto> = mutableListOf()

        listadoProyectosCreadosId?.forEach {
            listadoProyectosCreados.add(Gestor.gestorProyectos.obtenerProyectoId(it))
        }

        return listadoProyectosCreados
    }

    override fun obtenerProyectosCreadosUsuarioId(usuario: Usuario): MutableList<Int> {

        var listadoProyectosId: MutableList<Int> = mutableListOf()

        Db.conexion().collection("proyecto")
            .whereEqualTo("idPropietario", usuario.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var proyecto = document.toObject(Usuario::class.java)
                    listadoProyectosId.add(proyecto.id)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return listadoProyectosId
    }

    override fun obtenerProyectosCreadosUsuario(listadoProyectosCreadosId: MutableList<Int>): MutableList<Proyecto> {
        var proyectosCreadosUsuario: MutableList<Proyecto> = mutableListOf()

        listadoProyectosCreadosId.forEach { id ->
            var proyecto = Gestor.gestorProyectos.obtenerProyectoId(id)
            proyectosCreadosUsuario.add(proyecto)
        }

        return proyectosCreadosUsuario
    }
}