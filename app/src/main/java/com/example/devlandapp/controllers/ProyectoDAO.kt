package com.example.devlandapp.controllers

import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

interface ProyectoDAO {
    fun obtenerTodosProyectos(): MutableList<Proyecto>

    fun obtenerProyectosFiltrados(
        ubicacion: String?,
        modoTrabajo: String?,
        tecnologia: String?,
        idioma: String?,
        verProyectosLLenos: Boolean?
    ): MutableList<Proyecto>

    fun obtenerProyectoId(id: Int?): Proyecto
    fun registrarProyecto(proyecto: Proyecto): Boolean
    fun modificarProyecto(proyecto: Proyecto): Boolean
    fun eliminarProyecto(proyecto: Proyecto): Boolean
    fun rellenarUsuariosInteresados(proyecto: Proyecto): MutableList<Usuario>?
    fun rellenarUsuariosSeleccionados(proyecto: Proyecto): MutableList<Usuario>?
}