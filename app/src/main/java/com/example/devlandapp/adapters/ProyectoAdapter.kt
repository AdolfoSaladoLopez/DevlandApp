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

class ProyectoAdapter(
    var context: Context?,
    var textViewResourceId: Int,
    var elementos: MutableList<Proyecto>?,
) : BaseAdapter() {

    var visible: Boolean = true

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
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
            holder.corazon.setImageResource(R.drawable.outline_favorite_border_24)

            if (bandera.idPropietario != UsuarioData.usuario.id) {
                if (saberUsuariosInteresados(bandera)) {
                    holder.corazon.setImageResource(R.drawable.favorito_relleno)
                    visible = true
                }
                holder.corazon.visibility = View.VISIBLE
            } else {
                holder.corazon.visibility = View.INVISIBLE
            }
        }

        holder.corazon.setOnClickListener {

            if (visible) {
                holder.corazon.setImageResource(R.drawable.outline_favorite_border_24)

                UsuarioData.usuario.proyectosInteresadosId.remove(bandera.id)
                UsuarioData.usuario.proyectosInteresados?.remove(bandera)

                bandera.usuariosInteresadosId.remove(UsuarioData.usuario.id)
                bandera.usuariosInteresados.remove(UsuarioData.usuario)

                GlobalScope.launch(Dispatchers.IO) {
                    Gestor.gestorUsuarios.modificarUsuario(UsuarioData.usuario)
                    Gestor.gestorProyectos.modificarProyecto(bandera)
                }

                visible = false

            } else {
                holder.corazon.setImageResource(R.drawable.favorito_relleno)

                UsuarioData.usuario.proyectosInteresadosId.add(bandera.id)
                UsuarioData.usuario.proyectosInteresados?.add(bandera)

                bandera.usuariosInteresadosId.add(UsuarioData.usuario.id)
                bandera.usuariosInteresados.add(UsuarioData.usuario)

                GlobalScope.launch(Dispatchers.IO) {
                    Gestor.gestorUsuarios.modificarUsuario(UsuarioData.usuario)
                    Gestor.gestorProyectos.modificarProyecto(bandera)
                }
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

    private fun obtenerPropietarioProyecto(proyecto: Proyecto): Usuario {
        var usuario: Usuario = Usuario()
        UsuarioData.totalUsuarios.forEach {
            if (it.id == proyecto.idPropietario) {
                usuario = it
            }
        }

        return usuario
    }

}