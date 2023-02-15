package com.example.devlandapp.models

import com.google.firebase.firestore.Exclude

class Notificacion(
    var id: Int = 0,
    var texto: String? = "",
    var leido: Boolean = false,
    @get:Exclude var usuario: Usuario? = null,
    var idUsuario: Int = 0
    )