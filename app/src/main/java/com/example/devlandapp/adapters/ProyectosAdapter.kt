package com.example.devland.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.devlandapp.R
import com.example.devlandapp.models.Proyecto


class ProyectosAdapter(var listaProyectos: Array<Proyecto>) :
    RecyclerView.Adapter<ProyectosAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombreProyecto: TextView = itemView.findViewById(R.id.nombre)
        var descripcionProyecto: TextView = itemView.findViewById(R.id.descripcion)
        val cardView: CardView = itemView.findViewById(R.id.card_view_proyecto_otro)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.proyecto_concreto, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombreProyecto.text = listaProyectos[position].nombre
        holder.descripcionProyecto.text = listaProyectos[position].descripcion

        holder.cardView.setOnClickListener {

            //TODO: Borrar este toast
            Toast.makeText(
                holder.itemView.context,
                "Proyecto seleccionado: ${listaProyectos[position].nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return listaProyectos.size
    }
}