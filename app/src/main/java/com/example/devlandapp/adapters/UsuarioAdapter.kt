package com.example.devlandapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.devlandapp.R
import com.example.devlandapp.models.Usuario

class UsuarioAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    private var elementos: MutableList<Usuario>?,
) : BaseAdapter() {

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder
        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(R.layout.carta_usuarios_interesados, null)
            holder = ViewHolder()
            holder.nombre = vista.findViewById(R.id.tvNombreUsuario) as TextView
            holder.email = vista.findViewById(R.id.tvEmail) as TextView

            vista.tag = holder

        } else {
            holder = vista.tag as ViewHolder
        }
        val bandera = elementos!![position]

        holder.nombre.text = "${bandera.nombre} ${bandera.apellidos}"
        holder.email.text = bandera.email
        return vista

    }

    override fun getCount(): Int {
        return elementos!!.size
    }

    override fun getItem(position: Int): Usuario {
        return elementos!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal class ViewHolder {
        lateinit var nombre: TextView
        lateinit var email: TextView
    }
}