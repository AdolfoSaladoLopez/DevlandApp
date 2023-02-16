package com.example.devlandapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.devlandapp.databinding.ActivityDetallesUsuarioBinding

class DetallesUsuarioActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityDetallesUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}