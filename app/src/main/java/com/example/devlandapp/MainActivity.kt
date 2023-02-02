package com.example.devlandapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : DrawerBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}