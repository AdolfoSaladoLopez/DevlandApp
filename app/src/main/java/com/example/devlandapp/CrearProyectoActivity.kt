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
import java.time.LocalDateTime

class CrearProyectoActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityCrearProyectoBinding
    var tecnologia: String = ""
    var idioma: String = ""
    var ubicacion: String = ""
    var tiempo: String = ""
    var modoTrabajo: String = ""
    var ultimoId: Int = 0
    var listadoProyectos: MutableList<Proyecto> = mutableListOf()

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
                        idioma = "Aleman"
                    }

                    4 -> {
                        idioma = "Portugues"
                    }

                    5 -> {
                        idioma = "Italiano"
                    }

                    6 -> {
                        idioma = "Chino"
                    }

                    7 -> {
                        idioma = "Japones"
                    }

                    8 -> {
                        idioma = "Ruso"
                    }

                    9 -> {
                        idioma = "Arabe"
                    }

                    10 -> {
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
                        tiempo = "días"
                    }

                    1 -> {
                        tiempo = "meses"
                    }

                    2 -> {
                        tiempo = "años"
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
            val spinnerTiempo = findViewById<Spinner>(R.id.tiempo)

            if (comprobarnombre(editTextNombreProyecto.text.toString()) &&
                comprobartiempo(editTextDuracion.text.toString()) &&
                comprobardescripcion(editTextDescripcionProyecto.text.toString())
            ) {

                var nombreProyecto = editTextNombreProyecto!!.text.toString()
                var cantidadProyecto: Int = editTextCantidadProyecto!!.text.toString().toInt()
                var descripcionProyecto = editTextDescripcionProyecto!!.text.toString()
                var duracion = editTextDuracion!!.text.toString()
                var tiempoProyecto = "$duracion $tiempo"

                var proyecto: Proyecto = Proyecto()
                var usuario = UsuarioData.usuario

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
                    R.drawable.person,
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