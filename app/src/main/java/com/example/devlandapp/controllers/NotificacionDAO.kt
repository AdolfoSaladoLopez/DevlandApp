package com.example.devlandapp.controllers

import com.example.devlandapp.models.Notificacion
import com.example.devlandapp.models.Usuario

interface NotificacionDAO {

    fun obtenerTodasNotificaciones(): MutableList<Notificacion>
    fun obtenerTodasNotificacionesDeUnUsuario(usuario:Usuario): MutableList<Notificacion>
    fun obtenerNotificacionId(id: Int?): Notificacion
    fun registrarNotificacion(notificacion: Notificacion): Boolean
    fun leerNotificacion(notificacion: Notificacion): Boolean
    fun eliminarNotificacion(notificacion: Notificacion): Boolean
}