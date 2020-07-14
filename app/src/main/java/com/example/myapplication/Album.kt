package com.example.myapplication

class Album {
    var id: Int
    var nombreAlbum: String
    var lanzamiento: Int
    var discografica: String
    constructor(id: Int, nombre: String, lanzamiento: Int, discografica: String){
        this.id = id
        this.nombreAlbum = nombre
        this.lanzamiento = lanzamiento
        this.discografica = discografica
    }
}