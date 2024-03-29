package com.example.devlandapp.controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class ProyectoController : ProyectoDAO {
    override fun obtenerTodosProyectos(): MutableList<Proyecto> {
        val listadoTotalProyectos: MutableList<Proyecto> = mutableListOf()

        Db.conexion().collection("proyecto")
            .orderBy("fechaPublicacion")
            .get()
            .addOnSuccessListener {
                for (proyecto in it) {
                    listadoTotalProyectos.add((proyecto.toObject(Proyecto::class.java)))
                }
            }

        return listadoTotalProyectos
    }

    override fun obtenerProyectosFiltrados(
        ubicacion: String?,
        modoTrabajo: String?,
        tecnologia: String?,
        idioma: String?,
        verProyectosLLenos: Boolean?
    ): MutableList<Proyecto> {
        val listadoTotalProyectos: MutableList<Proyecto> = mutableListOf()

        print("ubicacion: $ubicacion")
        print("modoTrabajo: $modoTrabajo")
        print("tecnologia: $tecnologia")
        print("idioma: $idioma")

        val db = Db.conexion()
        val proyectosRef = db.collection("proyecto")

        var query = proyectosRef.whereEqualTo("estado", true)

        if (ubicacion != "-") {
            query = query.whereEqualTo("ubicacion", ubicacion)
        }

        if (modoTrabajo != "-") {
            query = query.whereEqualTo("modoTrabajo", modoTrabajo)
        }

        if (tecnologia != "-") {
            query = query.whereEqualTo("tecnologia", tecnologia)
        }

        if (idioma != "-") {
            query = query.whereEqualTo("idioma", idioma)
        }

        query.get()
            .addOnSuccessListener {
                for (proyecto in it) {
                    proyecto.toObject(Proyecto::class.java).let { it1 ->
                        if (verProyectosLLenos!!) {
                            listadoTotalProyectos.add(it1)
                        } else {
                            if (it1.usuariosSeleccionados.size < it1.numeroParticipantes!!) {
                                listadoTotalProyectos.add(it1)
                            } else {
                                Log.d(TAG, "Este proyecto no cumple este filtro")
                            }
                        }
                    }
                }
            }

        return listadoTotalProyectos
    }

    override fun obtenerProyectoId(id: Int?): Proyecto {
        var proyecto: Proyecto = Proyecto()

        val docRef = Db.conexion().collection("proyecto").document(id.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    proyecto = document.toObject(Proyecto::class.java)!!
                    println(proyecto.nombre)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return proyecto
    }

    override fun registrarProyecto(proyecto: Proyecto): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("proyecto")
            .document(proyecto.id.toString())
            .set(proyecto)

        if (proyecto.id?.let { obtenerProyectoId(it) } != null) {
            validador = true
        }

        return validador
    }

    override fun modificarProyecto(proyecto: Proyecto): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("proyecto")
            .document(proyecto.id.toString())
            .set(proyecto)

        if (proyecto.id?.let { obtenerProyectoId(it) } != null) {
            validador = true
        }

        return validador
    }

    override fun eliminarProyecto(proyecto: Proyecto): Boolean {
        var validador: Boolean = false

        Db.conexion().collection("proyecto")
            .document(proyecto.id.toString())
            .delete()
            .addOnSuccessListener { validador = true }

        return validador
    }

    override fun rellenarUsuariosInteresados(proyecto: Proyecto): MutableList<Usuario>? {
        val listadoUsuariosInteresadosId: MutableList<Int> = proyecto.usuariosInteresadosId
        val listadoUsuariosInteresados: MutableList<Usuario> = mutableListOf()

        listadoUsuariosInteresadosId.forEach {
            listadoUsuariosInteresados.add(Gestor.gestorUsuarios.obtenerUsuarioId(it))
        }

        return listadoUsuariosInteresados
    }

    override fun rellenarUsuariosSeleccionados(proyecto: Proyecto): MutableList<Usuario>? {
        val listadoUsuariosSeleccionadosId: MutableList<Int> = proyecto.usuariosSeleccionadosId
        val listadoUsuariosSeleccionados: MutableList<Usuario> = mutableListOf()

        listadoUsuariosSeleccionadosId.forEach {
            listadoUsuariosSeleccionados.add(Gestor.gestorUsuarios.obtenerUsuarioId(it))
        }

        return listadoUsuariosSeleccionados
    }
}