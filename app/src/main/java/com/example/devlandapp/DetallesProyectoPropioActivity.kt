package com.example.devlandapp

import android.os.Bundle
import android.widget.TextView
import com.example.devlandapp.models.Proyecto

class DetallesProyectoPropioActivity : DrawerBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val valor = intent.extras!!.getInt("id")
        var proyecto: Proyecto = Proyecto()
        UsuarioData.usuario.proyectosCreados?.forEach {
            if (it.id == valor) {
                proyecto = it
            }
        }

        super.onCreate(savedInstanceState)
        val textViewNombreProyecto = findViewById<TextView>(R.id.nombre3)
        val textViewDescripcionProyecto = findViewById<TextView>(R.id.descripcion3)
        val textViewUbicacion = findViewById<TextView>(R.id.ubicacion)
        val textViewModo = findViewById<TextView>(R.id.modo)
        val textViewTecnologias = findViewById<TextView>(R.id.tecnologias)
        val textViewGente = findViewById<TextView>(R.id.genteProyecto)
        val textViewFecha = findViewById<TextView>(R.id.fechaPublicacion)
        val textViewEstado = findViewById<TextView>(R.id.estado)
        val textViewIdioma = findViewById<TextView>(R.id.idioma)
        val textViewDuracion = findViewById<TextView>(R.id.duracion)
        val textViewUsuario = findViewById<TextView>(R.id.usuarioPropietario)

        textViewNombreProyecto.text = proyecto.nombre
        textViewDescripcionProyecto.text = proyecto.descripcion
        textViewUbicacion.text = proyecto.ubicacion
        textViewModo.text = proyecto.modoTrabajo
        textViewGente.text = proyecto.numeroParticipantes.toString()
        textViewFecha.text = proyecto.fechaPublicacion
        textViewEstado.text = proyecto.estado.toString()
        textViewIdioma.text = proyecto.idioma
        textViewDuracion.text = proyecto.duracion
        textViewUsuario.text = proyecto.propietario?.nombre
    }
}