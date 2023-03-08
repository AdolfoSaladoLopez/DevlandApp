package com.example.devlandapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class ImageAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    private var elementos: MutableList<Int>?,
) : BaseAdapter() {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder
        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(com.example.devlandapp.R.layout.grid_item, null)
            holder = ViewHolder()
            holder.imagen =
                vista.findViewById(com.example.devlandapp.R.id.imagen_seleccionable) as ImageView


            vista.tag = holder

        } else {
            holder = vista.tag as ViewHolder
        }
        val bandera = elementos!![position]

        if (bandera != null) {
            holder.imagen.setImageResource(bandera)
        }
        return vista

    }

    override fun getCount(): Int {
        return elementos!!.size
    }

    override fun getItem(position: Int): Int {
        return elementos!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal class ViewHolder {
        lateinit var imagen: ImageView

    }

}