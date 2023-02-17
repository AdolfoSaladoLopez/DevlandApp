package com.example.devlandapp.models

import com.example.devlandapp.R
import com.google.firebase.firestore.Exclude

data class Proyecto(
    var id: Int = 0,
    var nombre: String? = "",
    var descripcion: String? = "",
    var tecnologia: String? = "",
    var ubicacion: String? = "",
    var modoTrabajo: String? = "",
    var idioma: String? = "",
    var duracion: String? = "",
    var estado: Boolean = false,
    var numeroParticipantes: Int? = 0,
    @get:Exclude var propietario: Usuario? = null,
    var idPropietario: Int = 0,
    var imagen: Int = 0,
    var imagenTexto: String = "R.drawable.person",
    var fechaPublicacion: String = "",

    @get:Exclude var usuariosInteresados: MutableList<Usuario> = mutableListOf(),
    @get:Exclude var usuariosSeleccionados: MutableList<Usuario> = mutableListOf(),
    var usuariosInteresadosId: MutableList<Int> = mutableListOf(),
    var usuariosSeleccionadosId: MutableList<Int> = mutableListOf()
)