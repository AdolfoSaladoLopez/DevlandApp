package com.example.devlandapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.devlandapp.R
import com.example.devlandapp.models.Proyecto

class ProyectosInteresadosAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    private var elementos: MutableList<Proyecto>?,
) : BaseAdapter() {

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder

        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(R.layout.carta_proyectos_interesados, null)
            holder = ViewHolder()
            holder.titulo = vista.findViewById(R.id.titulo) as TextView
            holder.descripcion = vista.findViewById(R.id.descrip) as TextView
            holder.fecha = vista.findViewById(R.id.fecha) as TextView
            holder.propietario = vista.findViewById(R.id.propiet) as TextView

            vista.tag = holder
        } else {
            holder = vista.tag as ViewHolder
        }

        val bandera = elementos!![position]

        holder.titulo.text = bandera.nombre
        holder.descripcion.text = bandera.descripcion
        holder.fecha.text = bandera.fechaPublicacion
        holder.propietario.text =
            "${bandera.propietario?.nombre} ${bandera.propietario?.apellidos}"

        return vista
    }

    override fun getCount(): Int {
        return elementos!!.size
    }

    override fun getItem(position: Int): Proyecto {
        return elementos!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal class ViewHolder {
        lateinit var titulo: TextView
        lateinit var descripcion: TextView
        lateinit var propietario: TextView
        lateinit var fecha: TextView
    }
}