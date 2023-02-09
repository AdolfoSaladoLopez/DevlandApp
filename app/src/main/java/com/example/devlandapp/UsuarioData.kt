package com.example.devlandapp

import com.example.devlandapp.models.Usuario

class UsuarioData {
    companion object {
        var usuario: Usuario = Usuario()
        var ultimoId: Int = 0
        var ultimoIdUsuario: Int = 0
    }
}