package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Realizar_suscripcion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_suscripcion)
        title = "Suscripción"
        supportActionBar?.hide()

        val message = intent.getStringExtra(EXTRA_MESSAGE)
    }
}