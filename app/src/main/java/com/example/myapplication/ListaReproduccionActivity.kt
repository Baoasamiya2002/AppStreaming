package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import kotlinx.android.synthetic.main.activity_lista_reproduccion.*
import org.json.JSONArray
import org.json.JSONObject

class ListaReproduccionActivity : AppCompatActivity(), ResultadoListener {

    //lateinit var listaReproduccion:ListaReproduccion
    var listaReproduccion:ListaReproduccion = ListaReproduccion(6, "Lista Ed", R.drawable.image_logo_background)
    var listaRep:ArrayList<Cancion> = ArrayList()
    var tipoList: Int = 0
    var usuario: ArrayList<Int> = arrayListOf()

    //variables donde se guardan los datos de la api
    var listaCanciones: ArrayList<Cancion> = ArrayList()
    var albums: ArrayList<Album> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reproduccion)

        listaReproduccion = intent.getSerializableExtra("lista") as ListaReproduccion
        tipoList = intent.getIntExtra("tipoList", 0)
        usuario = intent.getIntegerArrayListExtra("idUsuario")
        //
        txtNombreListaRep.text = listaReproduccion.nombreLista
        imgListaRep.setImageResource(listaReproduccion.imagenLista)

        //getAlbums()
        getCanciones()

        btnReproducir.setOnClickListener{
            reproducir()
        }
    }

    fun getCanciones(){
        val solicitud = Solicitud(this)

        solicitud.solicitudArrayGet("/cancionlista_reproduccion/byId/" + listaReproduccion.idLista.toString(), this)

        /*
        val cancion1 = Cancion("Cancion1", "Album1", R.mipmap.ic_launcher_round, R.raw.song1)
        val cancion2 = Cancion("Cancion2", "Album1", R.mipmap.ic_launcher_round, R.raw.song2)
        val cancion3 = Cancion("Cancion3", "Album2", R.mipmap.ic_launcher_round, R.raw.song3)

        listaRep.add(cancion1)
        listaRep.add(cancion2)
        listaRep.add(cancion3)

        //PARA EL ADAPTADOR DE LA LISTA
        val listaCanciones = listOf(cancion1, cancion2, cancion3)
        val adapter = Cancion_Adapter(this, listaCanciones, this.layoutInflater)
        listCanciones.adapter = adapter
        */
    }

    //AÑADE LA LISTA DE REPRODUCCIÓN A LA COLA Y ABRE LA PANTALLA DEL REPRODUCTOR
    fun reproducir(){

        /*CODIGO PARA LA COLA GLOBAL
        *
        * val colaG = ColaReproduccion()
        * colaG.addCanciones(listaRep)
        * val intent = Intent (this, ReproductorActivity::class.java)
        * intent.putExtra("cr", colaG)
        * startActivity(intent)
        *
        * */



        val intent = Intent (this, ReproductorActivity::class.java)
        intent.putExtra("lista", listaRep)
        startActivity(intent)
    }

    fun getAlbums(){
        val solicitud = Solicitud(this)
        solicitud.solicitudArrayGet("/album", this)
    }

    override fun getResult(respuesta: JSONObject?) {

    }

    override fun getArrayResult(respuesta: JSONArray?) {
        if(respuesta != null){
            if(respuesta.getJSONObject(0).has("lista_reproduccionId")){
                var cont = 0
                while (cont < respuesta.length()) {
                    var json: JSONObject = respuesta[cont] as JSONObject
                    var cancion: Cancion = Cancion(
                        json.getJSONObject("cancion").getInt("id"),
                        json.getJSONObject("cancion").getString("nombre_cancion"),
                        json.getJSONObject("cancion").getString("artistaId"),
                        json.getJSONObject("cancion").getString("albumId"),
                        json.getJSONObject("cancion").getString("generoId"), 1, ""
                    )


                    listaCanciones.add(cancion)
                    cont++
                }
                getAlbums()
            }
            if(respuesta.getJSONObject(0).has("nombre_album")){
                var cont = 0
                while (cont < respuesta.length()){
                    var json: JSONObject = respuesta[cont] as JSONObject
                    var album: Album = Album(json.getInt("id"), json.getString("nombre_album"), json.getInt("lanzamiento"), json.getString("discografica"))
                    albums.add(album)
                    cont++

                    var cont2 = 0
                    while (cont2 < albums.size) {
                        var cont3 = 0
                        while (cont3 < listaCanciones.size){
                            if (albums[cont2].id.toString() == listaCanciones[cont3].album) {
                                listaCanciones[cont3].album = albums[cont2].nombreAlbum
                            }
                            cont3++
                        }
                        cont2++
                    }

                }


                listaRep = listaCanciones
                val adapter = Cancion_Adapter(this, listaCanciones, this.layoutInflater, usuario)
                listCanciones.adapter = adapter
            }
        }
    }
}