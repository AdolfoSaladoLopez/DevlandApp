package com.example.devlandapp

import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

open class DrawerBaseActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout

    override fun setContentView(view: View) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer_base, null) as DrawerLayout
        var contenedor: FrameLayout = drawerLayout.findViewById(R.id.contenedor)
        contenedor.addView(view)
        super.setContentView(drawerLayout)

        var toolbar: Toolbar = drawerLayout.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var navigationView = drawerLayout.findViewById<NavigationView>(R.id.nav)

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            when (it.itemId) {
                R.id.feed -> goToFeed()
                R.id.add -> goToCrearProyecto()
                R.id.projects -> goToMisProyectos()
                R.id.profile -> goToMiPerfil()

            }
            false
        }

        var toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.menu_drawer_open,
            R.string.menu_drawer_close
        )
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

    }

    protected fun localizarTituloActivity(titulo: String) {
        supportActionBar?.title = titulo
    }

    private fun goToFeed() {
        val intent = Intent(this, FeedActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun goToCrearProyecto() {
        val intent = Intent(this, CrearProyectoActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)

    }

    private fun goToMisProyectos() {
        val intent = Intent(this, MisProyectosActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)

    }

    private fun goToMiPerfil() {
        val intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

}

