package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_buscar.*
import org.json.JSONArray
import org.json.JSONObject


@Suppress("UNCHECKED_CAST")
class Buscar : Fragment(), ResultadoListener {
    var listalistaReproduccion : MutableList<ListaReproduccion> = mutableListOf<ListaReproduccion>()
    var listaalbum : MutableList<ListaReproduccion> = mutableListOf<ListaReproduccion>()

    companion object {
        fun newInstance () : Buscar = Buscar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_buscar, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val searchView = view?.findViewById<SearchView>(R.id.searchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.trim() == ""){
                    tvCancion.isGone = true
                    listaCancion.isGone = true
                    tvAlbum.isGone = true
                    listaAlbum.isGone = true
                    tvListaReproduccion.isGone = true
                    listaListaReproduccion.isGone = true
                    tvArtista.isGone = true
                    listaArtista.isGone = true
                } else {
                    busquedaPalabra(newText)
                }
                return false
            }
        })
        searchView?.setFocusable(true)
        searchView?.setIconified(false)

        listaAlbum.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val lista: ListaReproduccion = listaalbum[i]
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java).putExtra("lista", lista)
                        .putExtra("tipoList", 1)
                )
            }
        listaListaReproduccion.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val lista: ListaReproduccion = listalistaReproduccion[i]
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java).putExtra("lista", lista)
                        .putExtra("tipoList", 0)
                )
            }
    }

    private fun resultadosListasReproduccion(respuesta: JSONArray) {
        val list = mutableListOf<ListaReproduccion>()
        for (i in 0 until respuesta.length()) {

            val listaReproduccion = respuesta.getJSONObject(i)
            list.add(ListaReproduccion(listaReproduccion.getInt("id"),
                listaReproduccion.getString("nombre_lista"), R.mipmap.image_logo_foreground))
        }
        listalistaReproduccion = list
        val listaReproduccion = list.toTypedArray()
        val adapter = activity?.let { Lista_Adapter(listaReproduccion, it) }
        listaListaReproduccion.adapter = adapter

        tvListaReproduccion.isGone = false
        listaListaReproduccion.isGone = false
    }

    private fun resultadosArtistas(respuesta: JSONArray) {
        val list = arrayOfNulls<String>(respuesta.length())
        for (i in 0 until respuesta.length()) {

            val artista = respuesta.getJSONObject(i)
            list[i] = artista.getString("nombre_artista")
        }

        val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        listaArtista.adapter = adapter

        tvArtista.isGone = false
        listaArtista.isGone = false
    }

    private fun resultadosAlbumes(respuesta: JSONArray) {
        val list = mutableListOf<ListaReproduccion>()
        for (i in 0 until respuesta.length()) {

            val album = respuesta.getJSONObject(i)
            list.add(ListaReproduccion(album.getInt("id"),
                album.getString("nombre_album"), R.mipmap.image_logo_foreground))
        }
        listaalbum = list
        val listaReproduccion = list.toTypedArray()
        val adapter = activity?.let { Lista_Adapter(listaReproduccion, it) }
        listaAlbum.adapter = adapter

        tvAlbum.isGone = false
        listaAlbum.isGone = false
    }

    private fun resultadosCanciones(respuesta: JSONArray) {
        val list = mutableListOf<Cancion>()
        for (i in 0 until respuesta.length()) {

            val cancion = respuesta.getJSONObject(i)
            list.add(Cancion(cancion.getInt("id"),
                cancion.getString("nombre_cancion"), cancion.getString("album"),
                R.mipmap.image_logo_foreground))
        }
        val adapter = activity?.let { Cancion_Adapter(it, list, activity!!.layoutInflater) }
        listaCancion.adapter = adapter

        tvCancion.isGone = false
        listaCancion.isGone = false
    }

    private fun busquedaPalabra(palabraBusqueda : String) {
        val solicitud = Solicitud(activity)
        solicitud.solicitudArrayGet("/cancion/busqueda/$palabraBusqueda",this)
        solicitud.solicitudArrayGet("/album/busqueda/$palabraBusqueda",this)
        solicitud.solicitudArrayGet("/lista_reproduccion/buscar/$palabraBusqueda",this)
        solicitud.solicitudArrayGet("/artista/busqueda/$palabraBusqueda",this)
    }

    override fun getResult(respuesta: JSONObject?) {
    }

    override fun getArrayResult(respuesta: JSONArray?) {
        if (respuesta != null && respuesta.length() > 0) {
            if(respuesta.getJSONObject(0).has("nombre_cancion")){

                resultadosCanciones(respuesta)
            }
            else if(respuesta.getJSONObject(0).has("nombre_album")) {

                resultadosAlbumes(respuesta)
            }
            else if(respuesta.getJSONObject(0).has("nombre_lista")) {

                resultadosListasReproduccion(respuesta)
            }
            else if(respuesta.getJSONObject(0).has("nombre_artista")) {

                resultadosArtistas(respuesta)
            }
        }
    }
}