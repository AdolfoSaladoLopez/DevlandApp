package com.example.devlandapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.devlandapp.R
import com.example.devlandapp.UsuarioData
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UsuarioAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    var elementos: MutableList<Usuario>?,
) : BaseAdapter() {

    @SuppressLint("SetTextI18n")
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

        if (bandera != null) {

            holder.nombre.text = "${bandera.nombre} ${bandera.apellidos}"
            holder.email.text = bandera.email
        }
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

    /*private fun saberUsuariosInteresados(proyecto: Proyecto): Boolean {
        var estaInteresado = false
        UsuarioData.totalUsuarios.forEach { usuario ->
            usuario.proyectosInteresadosId.forEach { idProyecto ->
                if (idProyecto == proyecto.id) {
                    estaInteresado = true
                }
            }
        }

        return estaInteresado
    }*/
}