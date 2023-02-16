package com.example.devlandapp.controllers


class Gestor {

    companion object {
        var gestorUsuarios: UsuarioController = UsuarioController()
        var gestorProyectos: ProyectoController = ProyectoController()
        var gestorNotificaciones: NotificacionController = NotificacionController()

    }

}