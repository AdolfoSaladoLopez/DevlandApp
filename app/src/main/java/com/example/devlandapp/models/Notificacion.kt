package com.example.devlandapp.models

import com.google.firebase.firestore.Exclude

class Notificacion(
    var id: Int = 0,
    var texto: String? = "",
    var leido: Boolean = false,
    @get:Exclude var usuario: Usuario? = null,
    var idUsuario: Int = 0,
    @get:Exclude var proyecto: Proyecto? = null,
    var idProyecto: Int = 0
    ){

    override fun toString(): String {
        return "Notificacion(id=$id, texto=$texto, leido=$leido, idUsuario=$idUsuario)"
    }
}

