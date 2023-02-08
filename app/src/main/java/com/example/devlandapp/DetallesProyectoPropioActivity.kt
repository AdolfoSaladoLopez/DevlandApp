package com.example.devlandapp

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.devlandapp.databinding.ActivityDetallesProyectoPropioBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import com.google.android.material.chip.Chip
import kotlin.properties.Delegates

class DetallesProyectoPropioActivity : DrawerBaseActivity() {
    var proyecto: Proyecto = Proyecto()
    var valor by Delegates.notNull<Int>()

    /* VISTAS */
    lateinit var binding: ActivityDetallesProyectoPropioBinding
    lateinit var propietario: TextView
    lateinit var titulo: TextView
    lateinit var fechaPublicacion: TextView
    lateinit var tecnologia: TextView
    lateinit var descripcion: TextView
    lateinit var idioma: TextView
    lateinit var ubicacion: TextView
    lateinit var modoTrabajo: TextView
    lateinit var duracion: TextView
    lateinit var participantes: TextView
    lateinit var estado: Chip
    lateinit var btnInteresados: Button
    lateinit var btnSeleccionados: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetallesProyectoPropioBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recuperarIntent()
        obtenerProyecto()
        iniciarVistas()
        rellenarVistas()
    }

    private fun recuperarIntent() {
        valor = intent.extras!!.getInt("id")
    }

    private fun obtenerProyecto() {
        UsuarioData.usuario.proyectosCreados?.forEach {
            if (it.id == valor) {
                proyecto = it
                println("ESTOY AQUÍ, PERRO: " + proyecto.nombre)
            }
        }
    }

    private fun iniciarVistas() {
        propietario = binding.propietario
        titulo = binding.tituloProyecto
        fechaPublicacion = binding.fPublicacion
        tecnologia = binding.tecnologia
        descripcion = binding.descrip
        idioma = binding.idioma
        ubicacion = binding.ubicacion
        modoTrabajo = binding.modoTrabajo
        duracion = binding.duracion
        participantes = binding.participantes
        estado = binding.estado
        btnInteresados = binding.interesados
        btnSeleccionados = binding.seleccionados
    }

    private fun comprobarDisponibilidad(estado: Boolean): String {
        var disponibilidad: String = "No disponible"

        if (estado) {
            disponibilidad = "Disponible"
        }

        return disponibilidad
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun rellenarVistas() {
        propietario.text = "- ${UsuarioData.usuario.nombre} + ${UsuarioData.usuario.apellidos} -"
        titulo.text = proyecto.nombre
        fechaPublicacion.text = proyecto.fechaPublicacion
        tecnologia.text = proyecto.tecnologia?.uppercase()
        descripcion.text = proyecto.descripcion
        idioma.text = proyecto.idioma?.uppercase()
        ubicacion.text = proyecto.ubicacion?.uppercase()
        modoTrabajo.text = proyecto.modoTrabajo?.uppercase()
        duracion.text = proyecto.duracion
        participantes.text = proyecto.numeroParticipantes.toString()
        estado.text = comprobarDisponibilidad(proyecto.estado)?.uppercase()
        if (comprobarDisponibilidad(proyecto.estado).equals("Disponible")) {
            estado.chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
        } else {
            estado.chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))

        }
        darFuncionalidadBotones()
    }

    fun darFuncionalidadBotones() {
        btnInteresados.setOnClickListener {
            Toast.makeText(this, "BOTÓN INTERESADOS", Toast.LENGTH_SHORT).show()
        }
        btnSeleccionados.setOnClickListener {
            Toast.makeText(this, "BOTÓN SELECCIONADOS", Toast.LENGTH_SHORT).show()

        }
    }
}