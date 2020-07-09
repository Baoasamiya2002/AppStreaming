package com.example.myapplication

import java.io.Serializable

//class Cancion(val nombreCancion:String, val album:String, val imagen:Int, val song:Int)

class Cancion : Serializable{
    var nombreCancion: String
    var album: String
    var imagen: Int
    var song: Int

    constructor(nombreCancion: String, album: String, imagen: Int, song: Int){
        this.nombreCancion = nombreCancion
        this.album = album
        this.imagen = imagen
        this.song = song
    }
}