package com.example.devlandapp.controllers

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Db {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val db = Firebase.firestore

        fun conexion(): FirebaseFirestore {
            return db
        }
    }
}