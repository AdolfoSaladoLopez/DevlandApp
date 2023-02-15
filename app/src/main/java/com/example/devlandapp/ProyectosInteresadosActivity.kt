package com.example.devlandapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.devlandapp.adapters.ProyectoAdapter
import com.example.devlandapp.adapters.ProyectosInteresadosAdapter
import com.example.devlandapp.databinding.ActivityProyectosInteresadosBinding
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario

class ProyectosInteresadosActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityProyectosInteresadosBinding

    private var totalProyectos: MutableList<Proyecto> = mutableListOf()
    private var proyectosInteresadosId: MutableList<Int> = mutableListOf()
    private var proyectosInteresados: MutableList<Proyecto> = mutableListOf()
    private lateinit var lista: ListView
    private var usuario: Usuario = Usuario()

    init {
        /* Obtenemos el usuario de la sesión */
        usuario = UsuarioData.usuario

        /* Obtenemos todos los proyectos */
        totalProyectos.clear()
        totalProyectos.addAll(UsuarioData.totalProyectos)



        /* Obtenemos los Ids de los proyectos que el usuario está interesado */
        proyectosInteresadosId.clear()
        proyectosInteresadosId.addAll(usuario.proyectosInteresadosId)


        /* Convertimos los id de los proyectos en objetos Proyecto */
        proyectosInteresados.clear()
        proyectosInteresados.addAll(convertirIdEnProyectos(proyectosInteresadosId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProyectosInteresadosBinding.inflate(layoutInflater)
        localizarTituloActivity("Feed")
        setContentView(binding.root)

        val intent2 = Intent(this, DetallesProyectoOtraPersonaActivity::class.java)


        if (UsuarioData.hayCambios) {
            /* Convertimos los id de los proyectos en objetos Proyecto */
            proyectosInteresados.clear()
            proyectosInteresados.addAll(convertirIdEnProyectos(proyectosInteresadosId))

            recarga(proyectosInteresados)
            UsuarioData.hayCambios = false
        }


        /* Pasamos los proyectos al adapter */
        recarga(proyectosInteresados)


        /* Le añadimos OnClick a la lista */
        lista = findViewById(R.id.lista)
        lista.setOnItemClickListener { _, _, position, _ ->
            intent2.putExtra("id", proyectosInteresados[position].id)
            startActivity(intent2)
        }
    }

    private fun recarga(proyectos: MutableList<Proyecto>) {

        var list = findViewById<ListView>(R.id.lista)

        val adapter = ProyectosInteresadosAdapter(
            this,
            R.layout.activity_feed, proyectos
        )

        list.cacheColorHint = 0
        list.adapter = adapter
    }

    private fun convertirIdEnProyectos(proyectosId: MutableList<Int>): MutableList<Proyecto> {
        val proyectosConvertidos: MutableList<Proyecto> = mutableListOf()

        totalProyectos.forEach { proyecto ->
            proyectosId.forEach { id ->
                if (proyecto.id == id) {
                    proyectosConvertidos.add(proyecto)
                }
            }
        }

        return proyectosConvertidos
    }
}