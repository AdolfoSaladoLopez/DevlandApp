package com.example.devlandapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import com.example.devlandapp.databinding.ActivityCrearProyectoBinding
import com.example.devlandapp.models.Proyecto

class CrearProyectoActivity : DrawerBaseActivity() {
    lateinit var binding: ActivityCrearProyectoBinding
    var tecnologia: String = ""
    var idioma: String = ""
    var ubicacion: String = ""
    var tipoTiempo: String = ""
    var modoTrabajo: String = ""

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearProyectoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spinnerTecnologias = findViewById<Spinner>(R.id.tecnologia_nuevoProyecto)
        var spinnerIdiomas = findViewById<Spinner>(R.id.idioma)
        var spinnerUbicacion = findViewById<Spinner>(R.id.ubicacion)
        var spinnerDuracion = findViewById<Spinner>(R.id.tiempo)
        var spinnerModoTrabajo = findViewById<Spinner>(R.id.modoTrabajo)

        var listaTecnologias = resources.getStringArray(R.array.tecnologias)
        var listaIdiomas = resources.getStringArray(R.array.idiomas)
        var listaUbicacion = resources.getStringArray(R.array.ubicacion)
        var listaTiempo = resources.getStringArray(R.array.tiempo)
        var listaModoTrabajo = resources.getStringArray(R.array.modoTrabajo)

        val adaptador1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTecnologias)
        val adaptador2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaIdiomas)
        val adaptador3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUbicacion)
        val adaptadorTiempo = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTiempo)
        var adaptadorModoTrabjo =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listaModoTrabajo)

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
                when (position) {
                    0 -> {
                        tecnologia = "C"
                    }

                    1 -> {
                        tecnologia = "C++"
                    }

                    2 -> {
                        tecnologia = "C#"
                    }

                    3 -> {
                        tecnologia = "Java"
                    }

                    4 -> {
                        tecnologia = "Kotlin"
                    }

                    5 -> {
                        tecnologia = "HTML y CSS"
                    }

                    6 -> {
                        tecnologia = "Python"
                    }

                    7 -> {
                        tecnologia = "PHP"
                    }

                    8 -> {
                        tecnologia = "JavaScript"
                    }

                    9 -> {
                        tecnologia = "SQL"
                    }

                    10 -> {
                        tecnologia = "Swift"
                    }
                }
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
                when (position) {
                    0 -> {
                        idioma = "Español"
                    }

                    1 -> {
                        idioma = "Francés"
                    }

                    2 -> {
                        idioma = "Inglés"
                    }

                    3 -> {
                        idioma = "Portugués"
                    }

                    4 -> {
                        idioma = "Italiano"
                    }

                    5 -> {
                        idioma = "Chino"
                    }

                    6 -> {
                        idioma = "Japones"
                    }

                    7 -> {
                        idioma = "Ruso"
                    }

                    8 -> {
                        idioma = "Arabe"
                    }

                    9 -> {
                        idioma = "Coreano"
                    }
                }
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
                when (position) {
                    0 -> {
                        ubicacion = "Malaga"
                    }

                    1 -> {
                        ubicacion = "Sevilla"
                    }

                    2 -> {
                        ubicacion = "Granada"
                    }

                    3 -> {
                        ubicacion = "Almeria"
                    }

                    4 -> {
                        ubicacion = "Huelva"
                    }

                    5 -> {
                        ubicacion = "Cordoba"
                    }

                    6 -> {
                        ubicacion = "Cadiz"
                    }

                    7 -> {
                        ubicacion = "Madrid"
                    }

                    8 -> {
                        ubicacion = "Barcelona"
                    }
                }
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
                when (position) {
                    0 -> {
                        tipoTiempo = "Dias"
                    }

                    1 -> {
                        tipoTiempo = "Meses"
                    }

                    2 -> {
                        tipoTiempo = "Años"
                    }
                }
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
                when (position) {
                    0 -> {
                        modoTrabajo = "Remoto"
                    }

                    1 -> {
                        modoTrabajo = "Presencial"
                    }

                    2 -> {
                        modoTrabajo = "Mixto"
                    }
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.crear.setOnClickListener {

            var editTextNombreProyecto = findViewById<EditText>(R.id.etTiulo)
            var editTextCantidadProyecto = findViewById<EditText>(R.id.participantes)
            var editTextDescripcionProyecto = findViewById<EditText>(R.id.etDescripcion)
            var fechaActual = System.currentTimeMillis().toString()
            var editTextDuracion = findViewById<EditText>(R.id.duracion)
            var spinnerTiempo = findViewById<Spinner>(R.id.tiempo)


            if (comprobarnombre(editTextNombreProyecto.text.toString()) &&
                comprobartiempo(editTextDuracion.text.toString()) &&
                comprobardescripcion(editTextDescripcionProyecto.text.toString())
            ) {

                val nombreProyecto = editTextNombreProyecto!!.text.toString()
                val cantidadProyecto: Int = editTextCantidadProyecto!!.text.toString().toInt()
                val descripcionProyecto = editTextDescripcionProyecto!!.text.toString()
                val tiempo = editTextDuracion!!.text.toString()
                val tiempoProyecto = tiempo + spinnerTiempo

                var proyecto: Proyecto
                val usuario = UsuarioData.usuario

                /*proyecto = Proyecto(0,
                    nombreProyecto,descripcionProyecto,tecnologia,ubicacion,boxchecker(presencial_modo_nuevoProyecto,
                        teletrabajo_modo_nuevoProyecto),idioma,tiempoProyecto,switchchecker(estado_nuevoProyecto),cantidadProyecto,
                    usuario,usuario!!.id,0,fechaActual) */


                //println(proyecto)

                Toast.makeText(this, "Proyecto creado", Toast.LENGTH_SHORT).show()

                //usuario!!.proyectosCreados!!.add(proyecto)

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

}