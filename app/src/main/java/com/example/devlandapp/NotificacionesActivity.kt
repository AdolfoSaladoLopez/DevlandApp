package com.example.devlandapp

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.adapters.NotificacionAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.controllers.NotificacionController
import com.example.devlandapp.databinding.ActivityNotificacionesBinding
import com.example.devlandapp.databinding.NotificacionesItemBinding
import com.example.devlandapp.models.Notificacion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotificacionesActivity : DrawerBaseActivity() {

    private var listaNotificaciones: MutableList<Notificacion> = mutableListOf()
    lateinit var adapter: NotificacionAdapter
    lateinit var binding: ActivityNotificacionesBinding

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            var comprobante = true

            while (comprobante) {
                listaNotificaciones = Gestor.gestorNotificaciones.obtenerTodasNotificaciones()
                delay(1000)

                if (listaNotificaciones.size > 0) {
                    comprobante = false
                }
            }

            println("TAMAÑO DE LAS NOTIFICACIONES ${listaNotificaciones.size}")
            println("EL NOMBRE DE LA NOTIFICACION ES ${listaNotificaciones[0].mensaje}")
            println("EL NOMBRE DE LA NOTIFICACION ES ${listaNotificaciones[0].id}")

            val listViewNotificaciones =
                findViewById<android.widget.ListView>(R.id.listViewNotificaciones)
            adapter = NotificacionAdapter(
                listViewNotificaciones.context,
                R.layout.notificaciones_item,
                listaNotificaciones
            )
            listViewNotificaciones.adapter = adapter

            listViewNotificaciones.setOnItemClickListener { parent, view, position, id ->
                val notificacion = listaNotificaciones[position]
                notificacion.leido = true
                adapter.notifyDataSetChanged()
                //TODO: que te mande a la activity de la notificacion
            }

        }

    }
}