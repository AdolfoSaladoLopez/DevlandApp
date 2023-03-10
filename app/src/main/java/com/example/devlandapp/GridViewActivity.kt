package com.example.devlandapp

import android.os.Bundle
import android.view.View
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.devlandapp.adapters.ImageAdapter


class GridViewActivity : AppCompatActivity() {
    private var listadoImagenes: MutableList<Int> = mutableListOf()
    private var adaptador: ImageAdapter? = null
    private var ultimaImagen: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view)

        listadoImagenes.addAll(rellenarListadoImagenes())

        val gridView: GridView = findViewById(R.id.grid)
        adaptador = ImageAdapter(this, R.layout.activity_grid_view, listadoImagenes);
        gridView.adapter = adaptador

        var imagen: Int = 0

        gridView.choiceMode = GridView.CHOICE_MODE_MULTIPLE;
        gridView.setOnItemClickListener { parent, view, position, id ->

            imagen = listadoImagenes[position]
            view.setBackgroundColor(resources.getColor(R.color.teal_200))

            if (view != ultimaImagen) {
                ultimaImagen?.setBackgroundColor(resources.getColor(R.color.gris))
                ultimaImagen = view
            }

        }
    }

    private fun rellenarListadoImagenes(): MutableList<Int> {
        var listado: MutableList<Int> = mutableListOf()

        for (num in 1..30) {
            listado.add(R.drawable.default_icon)
        }

        return listado
    }
}