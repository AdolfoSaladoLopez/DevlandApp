package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.Intent
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
import com.google.android.material.chip.Chip
import kotlin.properties.Delegates

class DetallesProyectoPropioActivity : DrawerBaseActivity() {
    private var proyecto: Proyecto = Proyecto()
    private var valor by Delegates.notNull<Int>()

    /* VISTAS */
    private lateinit var binding: ActivityDetallesProyectoPropioBinding
    private lateinit var propietario: TextView
    private lateinit var titulo: TextView
    private lateinit var fechaPublicacion: TextView
    private lateinit var tecnologia: TextView
    private lateinit var descripcion: TextView
    private lateinit var idioma: TextView
    private lateinit var ubicacion: TextView
    private lateinit var modoTrabajo: TextView
    private lateinit var duracion: TextView
    private lateinit var participantes: TextView
    private lateinit var estado: Chip
    private lateinit var btnInteresados: Button
    private lateinit var btnSeleccionados: Button
    private lateinit var btnVerMasTarde: Button
    private lateinit var btnEstoyInteresado: Button
    private var totalProyectos: MutableList<Proyecto> = mutableListOf()

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
        totalProyectos.addAll(UsuarioData.totalProyectos)

        totalProyectos?.forEach {
            if (it.id == valor) {
                proyecto = it
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
        btnVerMasTarde = binding.verMasTarde
        btnEstoyInteresado = binding.estoyInteresado
    }

    private fun comprobarDisponibilidad(estado: Boolean): String {
        var disponibilidad = "No disponible"

        if (estado) {
            disponibilidad = "Disponible"
        }

        return disponibilidad
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun rellenarVistas() {
        propietario.text = "- ${UsuarioData.usuario.nombre} ${UsuarioData.usuario.apellidos} -"
        titulo.text = proyecto.nombre
        fechaPublicacion.text = proyecto.fechaPublicacion
        tecnologia.text = proyecto.tecnologia?.uppercase()
        descripcion.text = proyecto.descripcion
        idioma.text = proyecto.idioma?.uppercase()
        ubicacion.text = proyecto.ubicacion?.uppercase()
        modoTrabajo.text = proyecto.modoTrabajo?.uppercase()
        duracion.text = proyecto.duracion
        participantes.text = proyecto.numeroParticipantes.toString()
        estado.text = comprobarDisponibilidad(proyecto.estado).uppercase()
        if (comprobarDisponibilidad(proyecto.estado) == "Disponible") {
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
        }
        btnSeleccionados.setOnClickListener {
            Toast.makeText(this, "BOTÓN SELECCIONADOS", Toast.LENGTH_SHORT).show()

        }
    }

    private fun goToInteresados() {
        val intent = Intent(this, ProyectosInteresadosActivity::class.java)
        startActivity(intent)
    }
}