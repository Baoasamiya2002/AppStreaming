package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_buscar.*


@Suppress("UNCHECKED_CAST")
class Buscar : Fragment() {

    var arrayTextView : Array<TextView> = emptyArray()
    var arrayListView : Array<ListView> = emptyArray()

    companion object {
        fun newInstance () : Buscar = Buscar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_buscar, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvArtista?.isInvisible = true
        tvAlbum?.isInvisible = true
        tvCancion?.isInvisible = true
        tvListaReproduccion?.isInvisible = true

        val searchView = view?.findViewById<SearchView>(R.id.searchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                resultadosCanciones()
                resultadosAlbumes()
                resultadosArtistas()
                resultadosListasReproduccion()
                if(newText.trim() == ""){
                    for (i in 0..2) {
                        arrayTextView.get(i).isInvisible = true
                        arrayListView.get(i).setVisibility(View.INVISIBLE)
                    }
                } else {
                    for (i in 0..2) {
                        if(arrayListView.get(i).count > 0){
                            arrayTextView.get(i).isInvisible = false
                            arrayListView.get(i).setVisibility(View.VISIBLE)
                        }
                    }
                }
                return false
            }
        })
        searchView?.setFocusable(true)
        searchView?.setIconified(false)
    }

    private fun resultadosListasReproduccion() {
        listaListaReproduccion?.setVisibility(View.INVISIBLE)

        val list : List<Cancion> = emptyList()

        val adapter = activity?.let { Cancion_Adapter(it, list, activity!!.layoutInflater) }

        listaListaReproduccion.adapter = adapter

        agregarAArray(tvListaReproduccion, listaListaReproduccion)
    }

    private fun resultadosArtistas() {
        listaArtista?.setVisibility(View.INVISIBLE)

        val list : List<Cancion> = emptyList()

        val adapter = activity?.let { Cancion_Adapter(it, list, activity!!.layoutInflater) }

        listaArtista.adapter = adapter

        agregarAArray(tvArtista, listaArtista)
    }

    private fun resultadosAlbumes() {
        listaAlbum?.setVisibility(View.INVISIBLE)

        val cancion1 = Cancion("Walbum1", "autor1", "album1", "1",R.drawable.live_streaming,0)
        val cancion2 = Cancion("Walbum12", "autor1", "album1", "1",R.drawable.live_streaming,0)

        val list = listOf(cancion1, cancion2)

        val adapter = activity?.let { Cancion_Adapter(it, list, activity!!.layoutInflater) }

        listaAlbum.adapter = adapter

        agregarAArray(tvAlbum, listaAlbum)
    }

    private fun resultadosCanciones() {
        listaCancion?.setVisibility(View.INVISIBLE)

        val cancion1 = Cancion("We Together","autor1", "album1", "1", R.drawable.live_streaming,0)
        val cancion2 = Cancion("Wravity", "autor1", "album1", "1", R.drawable.live_streaming,0)
        val cancion3 = Cancion("WONEY TALK", "autor1", "album1", "1", R.drawable.live_streaming,0)
        val cancion4 = Cancion("Wou&I", "autor1", "album1", "1", R.drawable.live_streaming,0)
        val cancion5 = Cancion("Wind Me?", "autor1", "album1", "1",R.drawable.live_streaming,0)

        val list = listOf(cancion1, cancion2, cancion3, cancion4, cancion5)

        val adapter = activity?.let { Cancion_Adapter(it, list, activity!!.layoutInflater) }

        listaCancion.adapter = adapter

        agregarAArray(tvCancion, listaCancion)
    }

    private fun agregarAArray(textView: TextView?, listView: ListView?) {
        val listaTv: MutableList<TextView> = arrayTextView.toMutableList()
        textView?.let { listaTv.add(it) }
        arrayTextView = listaTv.toTypedArray()

        val listaLv: MutableList<ListView> = arrayListView.toMutableList()
        listView?.let { listaLv.add(it) }
        arrayListView = listaLv.toTypedArray()
    }


}