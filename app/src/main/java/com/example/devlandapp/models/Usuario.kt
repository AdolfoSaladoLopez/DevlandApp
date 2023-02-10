package com.example.devlandapp.models

import com.google.firebase.firestore.Exclude

data class Usuario(
    var id: Int = 0,
    var nombre: String? = "",
    var apellidos: String? = "",
    var email: String? = "",
    var password: String? = "",
    var administrador: Boolean? = null,
    var descripcion: String? = "",
    var imagen: Int? = 0,

    @get:Exclude var proyectosCreados: MutableList<Proyecto>? = mutableListOf(),
    @get:Exclude var proyectosInteresados: MutableList<Proyecto>? = mutableListOf(),
    var proyectosCreadosId: MutableList<Int>? = mutableListOf(),
    var proyectosInteresadosId: MutableList<Int>? = mutableListOf()

)