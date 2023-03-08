package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityCrearProyectoBinding
import com.example.devlandapp.models.Proyecto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CrearProyectoActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityCrearProyectoBinding
    var tecnologia: String = ""
    var idioma: String = ""
    var ubicacion: String = ""
    var tiempo: String = ""
    var modoTrabajo: String = ""
    private var ultimoId: Int = 0
    private var listadoProyectos: MutableList<Proyecto> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearProyectoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerTecnologias = findViewById<Spinner>(R.id.tecnologia_nuevoProyecto)
        val spinnerIdiomas = findViewById<Spinner>(R.id.idioma)
        val spinnerUbicacion = findViewById<Spinner>(R.id.ubicacion)
        val spinnerDuracion = findViewById<Spinner>(R.id.tiempo)
        val spinnerModoTrabajo = findViewById<Spinner>(R.id.modoTrabajo)

        val listaTecnologias = resources.getStringArray(R.array.tecnologias)
        val listaIdiomas = resources.getStringArray(R.array.idiomas)
        val listaUbicacion = resources.getStringArray(R.array.ubicacion)
        val listaTiempo = resources.getStringArray(R.array.tiempo)
        val listaModoTrabajo = resources.getStringArray(R.array.modoTrabajo)

        val adaptador1 = ArrayAdapter(this, R.layout.spinner_layout, listaTecnologias)
        val adaptador2 = ArrayAdapter(this, R.layout.spinner_layout, listaIdiomas)
        val adaptador3 = ArrayAdapter(this, R.layout.spinner_layout, listaUbicacion)
        val adaptadorTiempo = ArrayAdapter(this, R.layout.spinner_layout, listaTiempo)
        val adaptadorModoTrabjo =
            ArrayAdapter(this, R.layout.spinner_layout, listaModoTrabajo)


        spinnerTecnologias.adapter = adaptador1
        spinnerIdiomas.adapter = adaptador2
        spinnerUbicacion.adapter = adaptador3
        spinnerDuracion.adapter = adaptadorTiempo
        spinnerModoTrabajo.adapter = adaptadorModoTrabjo

        spinnerTecnologias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tecnologia = listaTecnologias[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerIdiomas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idioma = listaIdiomas[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerUbicacion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ubicacion = listaUbicacion[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerDuracion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tiempo = listaTiempo[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerModoTrabajo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                modoTrabajo = listaModoTrabajo[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.crear.setOnClickListener {
            var comprobante: Boolean = true

            lifecycleScope.launch {
                while (comprobante) {
                    listadoProyectos = Gestor.gestorProyectos.obtenerTodosProyectos()
                    delay(1000)

                    if (listadoProyectos[0].nombre != "") {
                        comprobante = false
                    }
                    Log.d(ContentValues.TAG, "Corriendo corrutina")
                }
            }

            ultimoId = listadoProyectos.size

            val editTextNombreProyecto = findViewById<EditText>(R.id.etTiulo)
            val editTextCantidadProyecto = findViewById<EditText>(R.id.participantes)
            val editTextDescripcionProyecto = findViewById<EditText>(R.id.etDescripcion)
            val editTextDuracion = findViewById<EditText>(R.id.duracion)

            if (comprobarnombre(editTextNombreProyecto.text.toString()) &&
                comprobartiempo(editTextDuracion.text.toString()) &&
                comprobardescripcion(editTextDescripcionProyecto.text.toString())
            ) {

                val nombreProyecto = editTextNombreProyecto!!.text.toString()
                val cantidadProyecto: Int = editTextCantidadProyecto!!.text.toString().toInt()
                val descripcionProyecto = editTextDescripcionProyecto!!.text.toString()
                val duracion = editTextDuracion!!.text.toString()
                val tiempoProyecto = "$duracion $tiempo"

                var proyecto: Proyecto = Proyecto()
                val usuario = UsuarioData.usuario

                proyecto = Proyecto(
                    UsuarioData.ultimoId,
                    nombreProyecto,
                    descripcionProyecto,
                    tecnologia,
                    ubicacion,
                    modoTrabajo,
                    idioma,
                    tiempoProyecto,
                    true,
                    cantidadProyecto,
                    usuario,
                    UsuarioData.usuario.id,
                    R.drawable.terminal,
                )

                if (Gestor.gestorProyectos.registrarProyecto(proyecto)) {
                    usuario.proyectosCreados?.add(proyecto)

                    if (Gestor.gestorUsuarios.modificarUsuario(usuario)) {
                        Toast.makeText(this, "Proyecto creado", Toast.LENGTH_SHORT).show()

                        goToFeed()
                    }
                }
            } else {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun comprobarnombre(nombre: String): Boolean {
        return !TextUtils.isEmpty(nombre)
    }

    private fun comprobardescripcion(cantidad: String): Boolean {
        return !TextUtils.isEmpty(cantidad)
    }

    private fun comprobartiempo(tiempo: String): Boolean {
        return !TextUtils.isEmpty(tiempo)
    }

    private fun goToFeed() {
        val intent = Intent(this, FeedActivity::class.java)
        // Evita que pasemos de nuevo a la activity login
        startActivity(intent)
    }


}