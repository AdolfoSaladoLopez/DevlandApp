package com.example.devlandapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.devlandapp.R
import com.example.devlandapp.UsuarioData
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.models.Proyecto

class ProyectoAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    private var elementos: MutableList<Proyecto>?,
) : BaseAdapter() {

    private var listadoProyectosInteresadosId: MutableList<Int>? = mutableListOf()

    init {
        listadoProyectosInteresadosId?.addAll(UsuarioData.usuario.proyectosInteresadosId)
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var visible: Boolean = false
        var vista = convertView
        val holder: ViewHolder

        if (vista == null) {
            val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vista = vi.inflate(R.layout.recycler_view_proyectos, null)
            holder = ViewHolder()
            holder.titulo = vista.findViewById(R.id.titulo) as TextView
            holder.descripcion = vista.findViewById(R.id.descrip) as TextView
            holder.fecha = vista.findViewById(R.id.fecha) as TextView
            holder.propietario = vista.findViewById(R.id.propiet) as TextView
            holder.corazon = vista.findViewById(R.id.corazon) as ImageView
            holder.imagenPerfil = vista.findViewById(R.id.ivImagenPerfil) as ImageView

            vista.tag = holder

        } else {

            holder = vista.tag as ViewHolder

        }

        val bandera = elementos!![position]

        holder.titulo.text = bandera.nombre
        holder.descripcion.text = bandera.descripcion
        holder.fecha.text = bandera.fechaPublicacion
        holder.propietario.text = "${bandera.propietario?.nombre} ${bandera.propietario?.apellidos}"

        bandera.imagen.let { holder.imagenPerfil.setImageResource(it) }

        if (bandera.idPropietario != UsuarioData.usuario.id) {
            if (saberUsuariosInteresados(bandera)) {
                holder.corazon.setImageResource(R.drawable.favorito_relleno)
                visible = true
            }
            holder.corazon.visibility = View.VISIBLE
        } else {
            holder.corazon.visibility = View.INVISIBLE
        }

        holder.corazon.setOnClickListener {
            if (visible) {
                UsuarioData.usuario.proyectosInteresadosId.remove(bandera.id)
                bandera.usuariosInteresadosId.remove(UsuarioData.usuario.id)

                Gestor.gestorUsuarios.modificarUsuario(UsuarioData.usuario)
                Gestor.gestorProyectos.modificarProyecto(bandera)

                holder.corazon.setImageResource(R.drawable.outline_favorite_border_24)

                visible = false
            } else {
                UsuarioData.usuario.proyectosInteresadosId.add(bandera.id)
                bandera.usuariosInteresadosId.add(UsuarioData.usuario.id)

                Gestor.gestorUsuarios.modificarUsuario(UsuarioData.usuario)
                Gestor.gestorProyectos.modificarProyecto(bandera)

                holder.corazon.setImageResource(R.drawable.favorito_relleno)

                visible = true
            }
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
        lateinit var corazon: ImageView
        lateinit var imagenPerfil: ImageView
    }

    private fun saberUsuariosInteresados(proyecto: Proyecto): Boolean {
        var estaInteresado = false

        listadoProyectosInteresadosId?.forEach { id ->
            if (id == proyecto.id) {
                estaInteresado = true
            }
        }

        return estaInteresado
    }
}