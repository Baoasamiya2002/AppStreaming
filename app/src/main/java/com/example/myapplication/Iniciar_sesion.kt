package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "com.example.myapplication.MESSAGE"

class Iniciar_sesion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        title = "Streaming Musica"
        supportActionBar?.hide()
    }

    fun clickbtnSuscribirse(view: View?) {
        val intent = Intent(this, Realizar_suscripcion::class.java).apply {
            putExtra(EXTRA_MESSAGE, "variableAPasar")
        }
        startActivity(intent)
    }

    fun clickbtnEntrar(view: View?) {
        val intent = Intent(this, Navegacion::class.java)
        startActivity(intent)
    }
}
