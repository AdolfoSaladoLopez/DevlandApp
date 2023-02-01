package com.example.devlandapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.devlandapp.R
import com.example.devlandapp.models.Proyecto

class ProyectoAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    var elementos: MutableList<Proyecto>?
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder
        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(R.layout.recycler_view_proyectos, null)
            holder = ViewHolder()
            holder.nombre = vista.findViewById(R.id.nombre2) as TextView
            holder.descripcion = vista.findViewById(R.id.descripcion2) as TextView
            vista.tag = holder
        } else {
            holder = vista.tag as ViewHolder
        }
        val bandera = elementos!![position]

        if (bandera != null) {
            holder.nombre.text = bandera.nombre
            holder.descripcion.text = bandera.descripcion
        }
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
        lateinit var nombre: TextView
        lateinit var descripcion: TextView
    }
}