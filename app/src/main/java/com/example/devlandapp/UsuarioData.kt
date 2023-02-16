package com.example.devlandapp

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.devlandapp.controllers.Gestor
import com.example.devlandapp.models.Proyecto
import com.example.devlandapp.models.Usuario
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UsuarioData {
    companion object {
        var usuario: Usuario = Usuario()
        var ultimoId: Int = 0
        var ultimoIdUsuario: Int = 0
        var totalProyectos: MutableList<Proyecto> = mutableListOf()
        var totalUsuarios: MutableList<Usuario> = mutableListOf()
        var hayCambios: Boolean = false

        fun traerTodosUsuarios(): MutableList<Usuario> {

            var comprobante = true
            GlobalScope.launch {
                while (comprobante) {
                    totalUsuarios = Gestor.gestorUsuarios.obtenerTodosUsuarios()
                    delay(1000)

                    if (totalUsuarios.size > 0) {
                        comprobante = false
                    }

                    UsuarioData.ultimoIdUsuario = totalUsuarios.size
                    Log.d(ContentValues.TAG, "Corriendo corrutina")
                }
            }

            return totalUsuarios
        }
    }
}