package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Iniciar_sesion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        title = "Streaming Musica"
        supportActionBar?.hide()
    }

}
