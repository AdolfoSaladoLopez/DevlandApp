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

class ProyectosInteresadosAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    var elementos: MutableList<Proyecto>?,
) : BaseAdapter() {

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
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

        if (bandera != null) {

            holder.titulo.text = bandera.nombre
            holder.descripcion.text = bandera.descripcion
            holder.fecha.text = bandera.fechaPublicacion
            holder.propietario.text =
                "${bandera.propietario?.nombre} ${bandera.propietario?.apellidos}"
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
        lateinit var titulo: TextView
        lateinit var descripcion: TextView
        lateinit var propietario: TextView
        lateinit var fecha: TextView
    }

    private fun saberUsuariosInteresados(proyecto: Proyecto): Boolean {
        var estaInteresado = false
        UsuarioData.totalUsuarios.forEach { usuario ->
            usuario.proyectosInteresadosId.forEach { idProyecto ->
                if (idProyecto == proyecto.id) {
                    estaInteresado = true
                }
            }
        }

        return estaInteresado
    }
}