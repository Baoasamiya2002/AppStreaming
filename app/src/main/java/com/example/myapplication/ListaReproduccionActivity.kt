package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_reproduccion.*

class ListaReproduccionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reproduccion)

        getCanciones()

        btnReproducir.setOnClickListener{
            val intent = Intent (this, ReproductorActivity::class.java)
            startActivity(intent)
        }
    }

    fun getCanciones(){
        val cancion1 = Cancion("Cancion1", "Album1", R.drawable.live_streaming)
        val cancion2 = Cancion("Cancion2", "Album1", R.drawable.live_streaming)
        val cancion3 = Cancion("Cancion3", "Album2", R.drawable.live_streaming)
        val cancion4 = Cancion("Cancion4", "Album2", R.drawable.live_streaming)
        val cancion5 = Cancion("Cancion5", "Album3", R.drawable.live_streaming)

        val listaCanciones = listOf(cancion1, cancion2, cancion3, cancion4, cancion5)

        val adapter = Cancion_Adapter(this, listaCanciones, this.layoutInflater)

       listCanciones.adapter = adapter
    }

    fun reproducir(){

    }
}