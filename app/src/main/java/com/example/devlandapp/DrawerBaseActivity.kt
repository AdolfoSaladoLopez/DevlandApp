package com.example.devlandapp

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.chip.ChipDrawable.createFromAttributes
import com.google.android.material.navigation.NavigationView

open class DrawerBaseActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout

    @SuppressLint("UnsafeOptInUsageError")
    override fun setContentView(view: View) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer_base, null) as DrawerLayout
        val contenedor: FrameLayout = drawerLayout.findViewById(R.id.contenedor)
        contenedor.addView(view)
        super.setContentView(drawerLayout)

        val toolbar: Toolbar = drawerLayout.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navigationView = drawerLayout.findViewById<NavigationView>(R.id.nav)

/*      TODO: Esto es para ponerle un badge a la notificacion
=======

>>>>>>> alejandro
        val menuItem = navigationView.menu.findItem(R.id.notifications)
        val icon = menuItem.icon

        val badgeDrawable = BadgeDrawable.create(this)
        badgeDrawable.backgroundColor = Color.RED
        badgeDrawable.isVisible = true
<<<<<<< HEAD

        //BadgeUtils.attachBadgeDrawable(badgeDrawable, icon, this)
        */




        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            when (it.itemId) {
                R.id.feed -> goToFeed()
                R.id.add -> goToCrearProyecto()
                R.id.projects -> goToMisProyectos()
                R.id.profile -> goToMiPerfil()
                R.id.notifications -> goToNotificaciones()
            }
            false
        }

        val toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(
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

    private fun goToNotificaciones() {
        val intent = Intent(this, NotificacionesActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun comprobarNotificacionesLeidas(): Boolean {

        return false
    }

    }



