package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_buscar.*


@Suppress("UNCHECKED_CAST")
class Buscar : Fragment() {

    var arrayListaBusqueda: Array<List<Any>> = emptyArray()
    var arrayAdapter: Array<ArrayAdapter<Any>> = emptyArray()
    var arrayTextView : Array<TextView> = emptyArray()
    var arrayListView : Array<ListView> = emptyArray()

    companion object {
        fun newInstance () : Buscar = Buscar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_buscar, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        resultadosCanciones()
        /*resultadosAlbumes()
        resultadosArtistas()
        resultadosListasReproduccion()*/

        val searchView = view?.findViewById<SearchView>(R.id.searchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val i = 0
                //for (i in 0..0) {
                    when(arrayListaBusqueda.get(i).first()){
                        is Cancion -> {
                            val arrayListaCancion = arrayListaBusqueda.get(i) as List<Cancion>
                            val arrayAdapterCancion = arrayAdapter.get(i) as ArrayAdapter<Cancion>
                            if (arrayListaCancion.stream().anyMatch{ o: Cancion -> o.nombreCancion.equals(query) }) {
                                arrayAdapterCancion.getFilter().filter(query)
                            }
                        }
                    }
                //}
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val i = 0
                if(newText.trim() == ""){
                    //for (i in 0..0) {
                        arrayTextView.get(i).isInvisible = true
                        arrayListView.get(i).setVisibility(View.INVISIBLE)
                    //}
                } else {
                    //for (i in 0..0) {
                        when(arrayListaBusqueda.get(i).first()){
                            is Cancion -> {
                                val arrayAdapterCancion = arrayAdapter.get(i) as Cancion_Adapter
                                arrayAdapterCancion.getFilter().filter(newText)
                            }
                        }
                        arrayTextView.get(i).isInvisible = false
                        arrayListView.get(i).setVisibility(View.VISIBLE)
                    //}
                }
                return false
            }
        })
        searchView?.setFocusable(true)
        searchView?.setIconified(false)
    }

    /*private fun resultadosListasReproduccion() {
        tvListaReproduccion?.isInvisible = true

        val listView = view?.findViewById<ListView>(R.id.listaListaReproduccion)
        listView?.setVisibility(View.INVISIBLE)

        val list = ArrayList<String>()
        list.add("2010's")
        list.add("pop ROCK EN-ES")
        list.add("aa")

        val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        listView?.setAdapter(adapter)

        agregarAArray(tvListaReproduccion, listView, list, adapter)
    }

    private fun resultadosArtistas() {
        tvArtista?.isInvisible = true

        val listView = view?.findViewById<ListView>(R.id.listaArtista)
        listView?.setVisibility(View.INVISIBLE)

        val list = ArrayList<String>()
        list.add("GIRLKIND")
        list.add("SISTAR19")
        list.add("HyunA")

        val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        listView?.setAdapter(adapter)

        agregarAArray(tvArtista, listView, list, adapter)
    }

    private fun resultadosAlbumes() {
        tvAlbum?.isInvisible = true

        val listView = view?.findViewById<ListView>(R.id.listaAlbum)
        listView?.setVisibility(View.INVISIBLE)

        val list = ArrayList<String>()
        list.add("Red Light")
        list.add("INNOCENT")
        list.add("Act. 7")

        val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        listView?.setAdapter(adapter)

        agregarAArray(tvAlbum, listView, list, adapter)
    }*/

    /*fun getCanciones(){
        val cancion1 = Cancion("Cancion1", "Album1", R.drawable.live_streaming)
        val cancion2 = Cancion("Cancion2", "Album1", R.drawable.live_streaming)
        val cancion3 = Cancion("Cancion3", "Album2", R.drawable.live_streaming)
        val cancion4 = Cancion("Cancion4", "Album2", R.drawable.live_streaming)
        val cancion5 = Cancion("Cancion5", "Album3", R.drawable.live_streaming)

        val listaCanciones = listOf(cancion1, cancion2, cancion3, cancion4, cancion5)

        val adapter = Cancion_Adapter(this, listaCanciones, this.layoutInflater)

        listCanciones.adapter = adapter
    }*/

    private fun resultadosCanciones() {
        tvCancion?.isInvisible = true

        listaCancion?.setVisibility(View.INVISIBLE)

        val cancion1 = Cancion("We Together", "Album1", R.drawable.live_streaming)
        val cancion2 = Cancion("Gravity", "Album1", R.drawable.live_streaming)
        val cancion3 = Cancion("MONEY TALK", "Album2", R.drawable.live_streaming)
        val cancion4 = Cancion("You&I", "Album2", R.drawable.live_streaming)
        val cancion5 = Cancion("Find Me?", "Album3", R.drawable.live_streaming)

        val list = listOf(cancion1, cancion2, cancion3, cancion4, cancion5)

        val adapter = activity?.let { Cancion_Adapter(it, list, activity!!.layoutInflater) }

        listaCancion.adapter = adapter

        agregarAArray(tvCancion, listaCancion, list, adapter as? (ArrayAdapter<Any>?))
    }

    private fun agregarAArray(textView: TextView?, listView: ListView?,
                              list: List<Any>, adapter: ArrayAdapter<Any>?) {
        val listaTv: MutableList<TextView> = arrayTextView.toMutableList()
        textView?.let { listaTv.add(it) }
        arrayTextView = listaTv.toTypedArray()

        val listaLv: MutableList<ListView> = arrayListView.toMutableList()
        listView?.let { listaLv.add(it) }
        arrayListView = listaLv.toTypedArray()

        val listaAl: MutableList<List<Any>> = arrayListaBusqueda.toMutableList()
        list.let { listaAl.add(it) }
        arrayListaBusqueda = listaAl.toTypedArray()

        val listaAa: MutableList<ArrayAdapter<Any>> = arrayAdapter.toMutableList()
        adapter?.let { listaAa.add(it) }
        arrayAdapter = listaAa.toTypedArray()
    }


}