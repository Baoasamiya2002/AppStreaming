package com.example.myapplication

import android.app.Application

class ColaReproduccion : Application() {

    var cola:ArrayList<Cancion> = ArrayList()

    fun setCancion(cancion:Cancion){
        cola.add(cancion)
    }
    fun setCanciones(canciones:ArrayList<Cancion>){
        cola.addAll(canciones)
    }
    fun getCanciones():ArrayList<Cancion>{
        return cola
    }

}