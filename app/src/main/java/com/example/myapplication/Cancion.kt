package com.example.myapplication

import java.io.Serializable

//class Cancion(val nombreCancion:String, val album:String, val imagen:Int, val song:Int)

class Cancion : Serializable{
    var id: Int
    var nombreCancion: String
    var autor: String
    var album: String
    var genero: String
    var imagen: Int
    var song: String

    constructor(id:Int,nombreCancion: String, autor: String, album: String, genero:String, imagen: Int, song: String){
        this.id = id
        this.nombreCancion = nombreCancion
        this.autor = autor
        this.album = album
        this.genero = genero
        this.imagen = imagen
        this.song = song
    }
}