package com.example.myapplication

import android.app.Application
import java.io.Serializable

class ColaReproduccion : Application(), Serializable {

    var cola:ArrayList<Cancion> = ArrayList()

    fun addCancion(cancion:Cancion){
        cola.add(cancion)
    }
    fun addCanciones(canciones:ArrayList<Cancion>){
        cola.addAll(canciones)
    }
    fun getCanciones():ArrayList<Cancion>{
        return cola
    }

}