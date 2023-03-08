package com.example.devlandapp

import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class UsuarioData {
    companion object {
        var usuario: Usuario = Usuario()
        var ultimoId: Int = 0
        var ultimoIdUsuario: Int = 0
        var totalProyectos: MutableList<Proyecto> = mutableListOf()
        var totalUsuarios: MutableList<Usuario> = mutableListOf()
        var hayCambios: Boolean = false
    }
}