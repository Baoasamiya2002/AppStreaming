package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_reproduccion.*
import org.json.JSONArray
import org.json.JSONObject

class ListaReproduccionActivity : AppCompatActivity(), ResultadoListener {

    lateinit var listaReproduccion:ListaReproduccion
    //var listaReproduccion:ListaReproduccion = ListaReproduccion(5, "Lista Ed", R.drawable.image_logo_background)
    var listaRep:ArrayList<Cancion> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reproduccion)

        listaReproduccion = intent.getSerializableExtra("listaReproduccion") as ListaReproduccion
        txtNombreListaRep.text = listaReproduccion.nombreLista
        imgListaRep.setImageResource(listaReproduccion.imagenLista)
        getCanciones()

        btnReproducir.setOnClickListener{
            reproducir()
        }
    }

    fun getCanciones(){
        val solicitud = Solicitud(this)

        solicitud.solicitudGet("/lista_reproduccion/listaReproducion/" + listaReproduccion.idLista.toString(), this)




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

    override fun getResult(respuesta: JSONObject?) {

    }

    override fun getArrayResult(respuesta: JSONArray?) {
        if(respuesta != null){
            val listaCanciones: ArrayList<Cancion> = ArrayList()
            var cont = 0
            while (cont < respuesta.length()){
                var cancion:Cancion = respuesta[cont] as Cancion
                listaCanciones.add(cancion)
            }
            listaRep = listaCanciones
            val adapter = Cancion_Adapter(this, listaCanciones, this.layoutInflater)
            listCanciones.adapter = adapter
        }
    }
}