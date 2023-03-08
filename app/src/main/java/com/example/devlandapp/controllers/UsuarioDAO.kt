package com.example.devlandapp.controllers

import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

interface UsuarioDAO {
    fun obtenerTodosUsuarios(): MutableList<Usuario>
    fun obtenerUsuarioId(id: Int): Usuario
    fun registrarUsuario(usuario: Usuario): Boolean
    fun modificarUsuario(usuario: Usuario): Boolean
    fun eliminarUsuario(usuario: Usuario): Boolean
    fun obtenerUsuarioCorreoElectronico(correo: String): Usuario?
    fun obtenerProyectosInteresados(usuario: Usuario?): MutableList<Proyecto>?
    fun obtenerProyectosInteresadosId(usuario: Usuario): MutableList<Int>?
    fun obtenerProyectosCreados(usuario: Usuario?): MutableList<Proyecto>?
    fun obtenerProyectosCreadosUsuarioId(usuario: Usuario): MutableList<Int>

    fun obtenerProyectosCreadosUsuario(listadoProyectosCreadosId: MutableList<Int>): MutableList<Proyecto>


}