package com.example.devlandapp.controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.devlandapp.models.Notificacion
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class NotificacionController : NotificacionDAO {
    override fun obtenerTodasNotificaciones(): MutableList<Notificacion> {
        val listadoTotalNotificaciones: MutableList<Notificacion> = mutableListOf()

        Db.conexion().collection("notificacion")
            .get()
            .addOnSuccessListener {
                for (notificacion in it) {
                    listadoTotalNotificaciones.add((notificacion.toObject(Notificacion::class.java)))
                }
            }
        return listadoTotalNotificaciones
    }

    override fun obtenerTodasNotificacionesDeUnUsuario(usuario: Usuario): MutableList<Notificacion> {
        val listadoTotalNotificaciones: MutableList<Notificacion> = mutableListOf()

        Db.conexion().collection("notificacion")
            .whereEqualTo("idUsuario", usuario.id)
            .get()
            .addOnSuccessListener {
                for (notificacion in it) {
                    val notif = notificacion.toObject(Notificacion::class.java)
                    notif.id =
                        notificacion.id.toInt()  // Asigna el ID de Firestore al objeto Notificacion
                    listadoTotalNotificaciones.add(notif)
                }
            }



        return listadoTotalNotificaciones
    }

    override fun registrarNotificacion(notificacion: Notificacion): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("notificacion")
            .document(notificacion.id.toString())
            .set(notificacion)

        if (notificacion.id?.let { obtenerNotificacionId(it) } != null) {
            validador = true
        }

        return validador
    }

    override fun eliminarNotificacionPorIdProyecto(proyecto: Proyecto): Boolean {
        var totalNotificaciones: MutableList<Notificacion> = obtenerTodasNotificaciones()
        var comprobante = false

        totalNotificaciones.forEach {
            Db.conexion().collection("notificacion")
                .document(it.idProyecto.toString())
                .delete()
                .addOnSuccessListener { comprobante = true }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
        return comprobante
    }

    override fun obtenerNotificacionId(id: Int?): Notificacion {
        var notificacion: Notificacion = Notificacion()

        val docRef = Db.conexion().collection("notificacion").document(id.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    notificacion = document.toObject(Notificacion::class.java)!!
                    println(notificacion.mensaje)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        notificacion.id = id!!

        return notificacion
    }
}