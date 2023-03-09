package com.example.devlandapp.controllers

import com.example.devlandapp.models.Notificacion
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

interface NotificacionDAO {
    fun obtenerTodasNotificaciones(): MutableList<Notificacion>
    fun obtenerTodasNotificacionesDeUnUsuario(usuario: Usuario): MutableList<Notificacion>
    fun obtenerNotificacionId(id: Int?): Notificacion
    fun registrarNotificacion(notificacion: Notificacion): Boolean
    fun eliminarNotificacionPorIdProyecto(proyecto: Proyecto): Boolean
}