package com.example.devlandapp.controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.devlandapp.models.Proyecto

class ProyectoController : ProyectoDAO {
    override fun obtenerTodosProyectos(): MutableList<Proyecto> {
        val listadoTotalProyectos: MutableList<Proyecto> = mutableListOf()


        Db.conexion().collection("proyecto")
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

        val docRef = Db.conexion().collection("proyecto").document(1.toString())
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
}