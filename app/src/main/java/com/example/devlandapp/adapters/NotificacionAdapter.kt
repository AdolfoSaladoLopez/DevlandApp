package com.example.devlandapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.devlandapp.R
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.models.Notificacion
import com.example.devlandapp.models.Proyecto

class NotificacionAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    var elementos: MutableList<Notificacion>?,
    ): BaseAdapter() {
    override fun getCount(): Int {
        return elementos?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return elementos?.get(position) ?: Proyecto()
    }

    override fun getItemId(id: Int): Long {
        return id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder
        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(R.layout.notificaciones_item, null)
            holder = ViewHolder()
            holder.textoTextView = vista.findViewById(R.id.textoNotificaciones) as TextView
            holder.vistoImageView = vista.findViewById(R.id.botonVer) as ImageView
            holder.cardView = vista.findViewById(R.id.cardViewNotificaciones) as CardView

            vista.tag = holder
        } else {
            holder = vista.tag as ViewHolder
        }
        val notificacion = elementos!![position]

        if (notificacion != null) {
            holder.textoTextView.text = notificacion.mensaje
            if (notificacion.leido) {
                holder.vistoImageView.setImageResource(R.drawable.ic_check_verde_oscuro)
                holder.cardView.setCardBackgroundColor(Color.parseColor("#C7C7C7"))
                holder.textoTextView.setTextColor(Color.parseColor("#6E6E6E"))
                actializarNotificacion(notificacion)


            } else {
                holder.vistoImageView.setImageResource(R.drawable.ic_check_gris)
                holder.cardView.setCardBackgroundColor(Color.parseColor("#5A0E66"))
                holder.textoTextView.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        holder.vistoImageView.setOnClickListener {
            notificacion.leido = true
            notifyDataSetChanged()
        }

        return vista
    }

    internal class ViewHolder {
        lateinit var textoTextView: TextView
        lateinit var vistoImageView: ImageView
        lateinit var cardView: CardView
    }

    private fun actializarNotificacion(notificacion: Notificacion) {
        Gestor.gestorNotificaciones.registrarNotificacion(notificacion)
    }
}