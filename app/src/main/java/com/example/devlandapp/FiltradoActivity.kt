package com.example.devlandapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.devlandapp.databinding.ActivityFeedBinding
import com.example.devlandapp.databinding.ActivityFiltradoBinding

class FiltradoActivity: DrawerBaseActivity() {
    private lateinit var binding: ActivityFiltradoBinding

    private lateinit var ubicacionElegida: String
    private lateinit var modoTrabajoElegido: String
    private lateinit var tecnologiaElegido: String
    private lateinit var idiomaElegido: String
    private var verProyectosLLenos = true

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltradoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerUbicacion = findViewById<Spinner>(R.id.spinnerUbicacion)
        val spinnerModoTrabajo = findViewById<Spinner>(R.id.spinnerModo)
        val spinnerTecnologia = findViewById<Spinner>(R.id.spinnerTecnologia)
        val spinnerIdioma = findViewById<Spinner>(R.id.spinnerIdioma)

        val listaUbicacion = resources.getStringArray(R.array.ubicacionFiltrado)
        val listaModo = resources.getStringArray(R.array.modoFiltrado)
        val listaTecnologias = resources.getStringArray(R.array.tecnologiasFiltrado)
        val listaIdiomas = resources.getStringArray(R.array.idiomasFiltrado)

        val adaptadorUbicacion = ArrayAdapter(this, R.layout.spinner_layout, listaUbicacion)
        val adaptadorModo = ArrayAdapter(this, R.layout.spinner_layout, listaModo)
        val adaptadorTecnologia = ArrayAdapter(this, R.layout.spinner_layout, listaTecnologias)
        val adaptadorIdioma = ArrayAdapter(this, R.layout.spinner_layout, listaIdiomas)

        spinnerUbicacion.adapter = adaptadorUbicacion
        spinnerModoTrabajo.adapter = adaptadorModo
        spinnerTecnologia.adapter = adaptadorTecnologia
        spinnerIdioma.adapter = adaptadorIdioma


        val botonBorrarUbicacion = findViewById<ImageView>(R.id.btnBorrarUbicacion)
        val botonBorrarModoTrabajo = findViewById<ImageView>(R.id.btnBorrarModo)
        val botonBorrarTecnologia = findViewById<ImageView>(R.id.btnBorrarTecnologia)
        val botonBorrarIdioma = findViewById<ImageView>(R.id.btnBorrarIdioma)

        val checkBoxProyectosLLenos = findViewById<CheckBox>(R.id.checkboxPlazas)

        val botonAceptar = findViewById<Button>(R.id.btnAceptar)
        val botonCancelar = findViewById<Button>(R.id.btnCancelar)

        spinnerUbicacion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ubicacionElegida = listaUbicacion[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerModoTrabajo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                modoTrabajoElegido = listaModo[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTecnologia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tecnologiaElegido = listaTecnologias[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idiomaElegido = listaIdiomas[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        checkBoxProyectosLLenos.setOnCheckedChangeListener { buttonView, isChecked ->
            verProyectosLLenos = isChecked
        }

        botonBorrarUbicacion.setOnClickListener {
            spinnerUbicacion.setSelection(0)
            ubicacionElegida = listaUbicacion[0]
        }

        botonBorrarModoTrabajo.setOnClickListener {
            spinnerModoTrabajo.setSelection(0)
            modoTrabajoElegido = listaModo[0]
        }

        botonBorrarTecnologia.setOnClickListener {
            spinnerTecnologia.setSelection(0)
            tecnologiaElegido = listaTecnologias[0]
        }

        botonBorrarIdioma.setOnClickListener {
            spinnerIdioma.setSelection(0)
            idiomaElegido = listaIdiomas[0]
        }

        botonAceptar.setOnClickListener {
            val intent = intent
            intent.putExtra("ubicacion", ubicacionElegida)
            intent.putExtra("modo", modoTrabajoElegido)
            intent.putExtra("tecnologia", tecnologiaElegido)
            intent.putExtra("idioma", idiomaElegido)
            intent.putExtra("verProyectosLLenos", verProyectosLLenos)
            setResult(RESULT_OK, intent)
            finish()
        }

        botonCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }


}