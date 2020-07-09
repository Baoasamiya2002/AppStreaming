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
import java.util.*


class Buscar : Fragment() {

    var arrayListaBusqueda: Array<ArrayList<String>> = emptyArray()
    var arrayAdapter: Array<ArrayAdapter<String>> = emptyArray()
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
        resultadosAlbumes()
        resultadosArtistas()
        resultadosListasReproduccion()

        val searchView = view?.findViewById<SearchView>(R.id.searchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                for (i in 0..3) {
                    if (arrayListaBusqueda.get(i).contains(query)) {
                        arrayAdapter.get(i).getFilter().filter(query)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.trim() == ""){
                    for (i in 0..3) {
                        arrayTextView.get(i).isInvisible = true
                        arrayListView.get(i).setVisibility(View.INVISIBLE)
                    }
                } else {
                    for (i in 0..3) {
                        arrayTextView.get(i).isInvisible = false
                        arrayListView.get(i).setVisibility(View.VISIBLE)
                        arrayAdapter.get(i).getFilter().filter(newText)
                    }
                }
                return false
            }
        })
        searchView?.setFocusable(true)
        searchView?.setIconified(false)
    }

    private fun resultadosListasReproduccion() {
        val tvListaReproduccion = view?.findViewById<TextView>(R.id.tvListaReproduccion)
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
        val tvArtista = view?.findViewById<TextView>(R.id.tvArtista)
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
        val tvAlbum = view?.findViewById<TextView>(R.id.tvAlbum)
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
    }

    private fun resultadosCanciones() {
        val tvCancion = view?.findViewById<TextView>(R.id.tvCancion)
        tvCancion?.isInvisible = true

        val listView = view?.findViewById<ListView>(R.id.listaCancion)
        listView?.setVisibility(View.INVISIBLE)

        val list = ArrayList<String>()
        list.add("Gentleman")
        list.add("All Fired Up")
        list.add("Not Givin Up")

        val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        listView?.setAdapter(adapter)

        agregarAArray(tvCancion, listView, list, adapter)
    }

    private fun agregarAArray(textView: TextView?, listView: ListView?,
                              list: ArrayList<String>, adapter: ArrayAdapter<String>?) {
        val listaTv: MutableList<TextView> = arrayTextView.toMutableList()
        textView?.let { listaTv.add(it) }
        arrayTextView = listaTv.toTypedArray()

        val listaLv: MutableList<ListView> = arrayListView.toMutableList()
        listView?.let { listaLv.add(it) }
        arrayListView = listaLv.toTypedArray()

        val listaAl: MutableList<ArrayList<String>> = arrayListaBusqueda.toMutableList()
        list.let { listaAl.add(it) }
        arrayListaBusqueda = listaAl.toTypedArray()

        val listaAa: MutableList<ArrayAdapter<String>> = arrayAdapter.toMutableList()
        adapter?.let { listaAa.add(it) }
        arrayAdapter = listaAa.toTypedArray()
    }

}