package com.example.devlandapp.models

import android.os.Build
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime

data class Proyecto constructor(
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
    var fechaPublicacion: String = obtenerFechaActual(),

    @get:Exclude var usuariosInteresados: MutableList<Usuario> = mutableListOf(),
    @get:Exclude var usuariosSeleccionados: MutableList<Usuario> = mutableListOf(),
    var usuariosInteresadosId: MutableList<Int> = mutableListOf(),
    var usuariosSeleccionadosId: MutableList<Int> = mutableListOf()
)


private fun obtenerFechaActual(): String {
    val datetime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    var dia = ""
    var mes = ""

    val pair = convertirDiasMeses(datetime, dia, mes)
    dia = pair.first
    mes = pair.second

    val fechaActual = "${dia}/${mes}/${datetime.year}"
    return fechaActual
}


private fun convertirDiasMeses(
    datetime: LocalDateTime,
    dia: String,
    mes: String
): Pair<String, String> {
    var dia1 = dia
    var mes1 = mes
    if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datetime.dayOfMonth < 10
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dia1 = "0${datetime.dayOfMonth}"
        }
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dia1 = datetime.dayOfMonth.toString()
        }
    }

    if (datetime.monthValue < 10) {
        mes1 = "0${datetime.monthValue}"
    } else {
        mes1 = datetime.monthValue.toString()
    }
    return Pair(dia1, mes1)
}