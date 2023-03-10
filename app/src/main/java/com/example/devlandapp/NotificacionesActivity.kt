package com.example.devlandapp

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.UsuarioData.Companion.usuario
import com.example.devlandapp.adapters.NotificacionAdapter
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.databinding.ActivityNotificacionesBinding
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
                listaNotificaciones =
                    Gestor.gestorNotificaciones.obtenerTodasNotificacionesDeUnUsuario(usuario)

                delay(1000)

                if (listaNotificaciones.size > 0) {
                    comprobante = false
                }
            }

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
                val intent = Intent(
                    this@NotificacionesActivity,
                    DetallesProyectoOtraPersonaActivity::class.java
                )
                intent.putExtra("id", notificacion.idProyecto)
                startActivity(intent)
            }
        }
    }
}