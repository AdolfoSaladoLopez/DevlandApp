package com.example.devlandapp

import androidx.appcompat.app.AppCompatActivity
import com.example.devlandapp.adapters.NotificacionAdapter
import com.example.devlandapp.databinding.ActivityNotificacionesBinding
import com.example.devlandapp.databinding.NotificacionesItemBinding
import com.example.devlandapp.models.Notificacion

class NotificacionesActivity: DrawerBaseActivity() {

    lateinit var listaNotificaciones: ArrayList<Notificacion>
    lateinit var adapter: NotificacionAdapter
    lateinit var binding: ActivityNotificacionesBinding

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)



        listaNotificaciones = ArrayList()
        listaNotificaciones.add(Notificacion(1, "Notificacion 1", false, null, 1))
        listaNotificaciones.add(Notificacion(2, "Notificacion 2", false, null, 1))
        listaNotificaciones.add(Notificacion(3, "Notificacion 3, la cual va a tener un texto mas largo si no te importa jjajajajajajajaa", true, null, 1))

        val listViewNotificaciones = findViewById<android.widget.ListView>(R.id.listViewNotificaciones)
        adapter = NotificacionAdapter(this, R.layout.notificaciones_item, listaNotificaciones)
        listViewNotificaciones.adapter = adapter


        listViewNotificaciones.setOnItemClickListener { parent, view, position, id ->
            val notificacion = listaNotificaciones[position]
            notificacion.leido = true
            adapter.notifyDataSetChanged()
            //TODO: que te mande a la activity de la notificacion
        }

    }
}