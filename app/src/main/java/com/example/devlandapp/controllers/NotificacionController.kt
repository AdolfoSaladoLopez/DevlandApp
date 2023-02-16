package com.example.devlandapp.controllers

import com.example.devlandapp.models.Notificacion
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class NotificacionController: NotificacionDAO {
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
        TODO("Not yet implemented")
    }

    override fun obtenerNotificacionId(id: Int?): Notificacion {
        TODO("Not yet implemented")
    }

    override fun registrarNotificacion(notificacion: Notificacion): Boolean {
        TODO("Not yet implemented")
    }

    override fun leerNotificacion(notificacion: Notificacion): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarNotificacion(notificacion: Notificacion): Boolean {
        TODO("Not yet implemented")
    }
}