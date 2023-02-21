package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityDetallesProyectoPropioBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
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
    private lateinit var editar: ImageView
    private lateinit var btnEliminar: Button
    private var totalUsuario: MutableList<Usuario> = mutableListOf()

    init {
        totalProyectos.clear()
        totalUsuario.clear()
        totalProyectos.addAll(UsuarioData.totalProyectos)
        totalUsuario.addAll(UsuarioData.totalUsuarios)
    }

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
        totalProyectos.forEach {
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
        btnInteresados = binding.btnInteresados
        btnSeleccionados = binding.btnSeleccionados
        btnVerMasTarde = binding.verMasTarde
        btnEstoyInteresado = binding.estoyInteresado
        editar = binding.ivEditar
        btnEliminar = binding.dppEliminarProyecto
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

    private fun darFuncionalidadBotones() {
        var idProyecto = proyecto.id
        btnInteresados.setOnClickListener {
            val intent4 = Intent(this, UsuariosInteresadosActivity::class.java)
            intent4.putExtra("proyecto", idProyecto)
            println(proyecto.id)
            startActivity(intent4)
        }
        btnSeleccionados.setOnClickListener {
            val intent = Intent(this, UsuariosSeleccionadosActivity::class.java)
            intent.putExtra("idProyecto", idProyecto)
            startActivity(intent)
        }
        editar.setOnClickListener{
            val intent = Intent(this,EditarProyectoActivity::class.java)
            intent.putExtra("idproyecto", idProyecto)
            startActivity(intent)
        }
        btnEliminar.setOnClickListener{

            totalUsuario.forEach { usuario: Usuario ->
                usuario.proyectosInteresadosId.forEach { id : Int ->

                    if(id == idProyecto){

                        usuario.proyectosInteresadosId.remove(id)
                        Gestor.gestorUsuarios.modificarUsuario(usuario)
                    }
                }
                Gestor.gestorProyectos.eliminarProyecto(proyecto)
                UsuarioData.totalProyectos.remove(proyecto)
            }
            
            val intent = Intent(this,MisProyectosActivity::class.java)
            startActivity(intent)
        }
    }
}