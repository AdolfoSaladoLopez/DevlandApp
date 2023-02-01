package com.example.devlandapp.controllers

import com.example.devlandapp.models.Usuario

interface UsuarioDAO {
    fun obtenerTodosUsuarios(): MutableList<Usuario>
    fun obtenerUsuarioId(id: Int): Usuario
    fun registrarUsuario(usuario: Usuario): Boolean
    fun modificarUsuario(usuario: Usuario): Boolean
    fun eliminarUsuario(usuario: Usuario): Boolean
}