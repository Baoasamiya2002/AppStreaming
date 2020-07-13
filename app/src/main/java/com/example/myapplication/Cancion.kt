package com.example.myapplication

import java.io.Serializable

//class Cancion(val nombreCancion:String, val album:String, val imagen:Int, val song:Int)

class Cancion : Serializable{
    var nombreCancion: String
    var autor: String
    var album: String
    var genero: String
    var imagen: Int
    var song: Int

    constructor(nombreCancion: String, autor: String, album: String, genero:String, imagen: Int, song: Int){
        this.nombreCancion = nombreCancion
        this.autor = autor
        this.album = album
        this.genero = genero
        this.imagen = imagen
        this.song = song
    }
}