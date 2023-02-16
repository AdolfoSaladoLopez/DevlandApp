package com.example.devlandapp.controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class ProyectoController : ProyectoDAO {
    override fun obtenerTodosProyectos(): MutableList<Proyecto> {
        val listadoTotalProyectos: MutableList<Proyecto> = mutableListOf()

        Db.conexion().collection("proyecto")
            .orderBy("fechaPublicacion")
            .get()
            .addOnSuccessListener {
                for (proyecto in it) {
                    listadoTotalProyectos.add((proyecto.toObject(Proyecto::class.java)))
                }
            }

        return listadoTotalProyectos
    }

    override fun obtenerProyectoId(id: Int?): Proyecto {
        var proyecto: Proyecto = Proyecto()

        val docRef = Db.conexion().collection("proyecto").document(id.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    proyecto = document.toObject(Proyecto::class.java)!!
                    println(proyecto.nombre)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return proyecto
    }

    override fun registrarProyecto(proyecto: Proyecto): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("proyecto")
            .document(proyecto.id.toString())
            .set(proyecto)

        if (proyecto.id?.let { obtenerProyectoId(it) } != null) {
            validador = true
        }

        return validador
    }

    override fun modificarProyecto(proyecto: Proyecto): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("proyecto")
            .document(proyecto.id.toString())
            .set(proyecto)

        if (proyecto.id?.let { obtenerProyectoId(it) } != null) {
            validador = true
        }

        return validador
    }

    override fun eliminarProyecto(proyecto: Proyecto): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("proyecto")
            .document(proyecto.id.toString())
            .delete()
            .addOnSuccessListener { validador = true }

        return validador
    }

    override fun rellenarUsuariosInteresados(proyecto: Proyecto): MutableList<Usuario>? {
        var listadoUsuariosInteresadosId: MutableList<Int> = proyecto.usuariosInteresadosId
        var listadoUsuariosInteresados: MutableList<Usuario> = mutableListOf()

        listadoUsuariosInteresadosId.forEach {
            listadoUsuariosInteresados.add(Gestor.gestorUsuarios.obtenerUsuarioId(it))
        }

        return listadoUsuariosInteresados
    }

    override fun rellenarUsuariosSeleccionados(proyecto: Proyecto): MutableList<Usuario>? {
        var listadoUsuariosSeleccionadosId: MutableList<Int> = proyecto.usuariosSeleccionadosId
        var listadoUsuariosSeleccionados: MutableList<Usuario> = mutableListOf()

        listadoUsuariosSeleccionadosId.forEach {
            listadoUsuariosSeleccionados.add(Gestor.gestorUsuarios.obtenerUsuarioId(it))
        }

        return listadoUsuariosSeleccionados
    }
}