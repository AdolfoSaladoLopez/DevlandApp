package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.devlandapp.UsuarioData.Companion.usuario
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesProyectoOtroBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import com.google.android.material.chip.Chip
import kotlin.properties.Delegates

class DetallesProyectoOtraPersonaActivity : DrawerBaseActivity() {
    private var proyecto: Proyecto = Proyecto()
    private var valor by Delegates.notNull<Int>()

    /* VISTAS */
    private lateinit var binding: ActivityDetallesProyectoOtroBinding
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
    private lateinit var btnVerMasTarde: Button
    private lateinit var btnEstoyInteresado: Button
    private var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var totalUsuarios: MutableList<Usuario> = mutableListOf()
    private var propiet: Usuario? = null
    private var interesado: Boolean = false
    private var listadoProyectosInteresadosId: MutableList<Int> = mutableListOf()

    init {
        listadoProyectosInteresadosId.addAll(UsuarioData.usuario.proyectosInteresadosId)
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetallesProyectoOtroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recuperarIntent()
        obtenerProyecto()
        obtenerPropietario()
        iniciarVistas()
        rellenarVistas()

        if (saberUsuariosInteresados(proyecto)) {
            btnEstoyInteresado.text = "Dejar de interesarme"
            interesado = true
        } else {
            btnEstoyInteresado.text = "Interesarme"
        }

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

    private fun obtenerPropietario() {
        totalUsuarios.addAll(UsuarioData.totalUsuarios)

        totalUsuarios?.forEach {
            if (it.id == proyecto.idPropietario) {
                propiet = it
                println(propiet!!.nombre)
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
        propietario.text = "- ${propiet?.nombre} ${propiet?.apellidos} -"
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

    @SuppressLint("SetTextI18n")
    fun darFuncionalidadBotones() {
        btnEstoyInteresado = findViewById(R.id.estoyInteresado)
        propietario = findViewById(R.id.propietario)

        btnEstoyInteresado.setOnClickListener {
            if (!interesado ) {
                usuario.proyectosInteresadosId.add(proyecto.id)
                proyecto.usuariosInteresadosId.add(usuario.id)

                btnEstoyInteresado.text = "Dejar de interesarme"

                Gestor.gestorProyectos.modificarProyecto(proyecto)
                Gestor.gestorUsuarios.modificarUsuario(usuario)

                interesado = true
            } else {
                usuario.proyectosInteresadosId.remove(proyecto.id)
                proyecto.usuariosInteresadosId.remove(usuario.id)

                Gestor.gestorUsuarios.modificarUsuario(usuario)
                Gestor.gestorProyectos.modificarProyecto(proyecto)

                btnEstoyInteresado.text = "Interesarme"
                interesado = false
            }
        }

        propietario.setOnClickListener{

            val intent = Intent(this, PerfilOtroUsuarioActivity::class.java)
            intent.putExtra("idpropiet", propiet?.id)

            val arrayList: ArrayList<Int> = arrayListOf()
            arrayList.add(propiet!!.id)
            arrayList.add(proyecto.id)
            intent.putExtra("id", arrayList)

            this.startActivity(intent)

        }
    }

    private fun saberUsuariosInteresados(proyecto: Proyecto): Boolean {
        var estaInteresado = false

        listadoProyectosInteresadosId?.forEach { id ->
            if (id == proyecto.id) {
                estaInteresado = true
            }
        }

        return estaInteresado
    }

}
