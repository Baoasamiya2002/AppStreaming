package com.example.myapplication

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.item_listareproduccion.view.*


@Suppress("UNCHECKED_CAST")
class Inicio : Fragment() {
    var listaListaReproduccion : Array<ListaReproduccion> = emptyArray()
    var listaRadioGenero : Array<ListaReproduccion> = emptyArray()

    companion object {
        fun newInstance () : Inicio = Inicio()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_inicio, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getMiListaReproduccion()
        getRadioGenero()

        val caListaReproduccion = activity?.let { CustomAdapter(listaListaReproduccion, it) }
        val caRadioGenero = activity?.let { CustomAdapter(listaRadioGenero, it) }

        gvListaReproduccion.adapter = caListaReproduccion
        gvRadio.adapter = caRadioGenero

        gvListaReproduccion.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val idLista: Int = listaListaReproduccion.get(i).idLista
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java)//.putExtra("idLista", idLista)
                )
            }
        gvRadio.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val idLista: Int = listaRadioGenero.get(i).idLista
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java)//.putExtra("idLista", idLista)
                )
            }
    }

    private fun getRadioGenero() {
        val lista1 = ListaReproduccion(7, "ListaGenero1", R.mipmap.image_logo_foreground)
        val lista2 = ListaReproduccion(8, "ListaGenero2", R.mipmap.image_logo_foreground)
        val lista4 = ListaReproduccion(12, "ListaGenero4", R.mipmap.image_logo_foreground)

        val list: MutableList<ListaReproduccion> = listaRadioGenero.toMutableList()
        list.add(lista1)
        list.add(lista2)
        list.add(lista4)
        listaRadioGenero = list.toTypedArray()
    }

    private fun getMiListaReproduccion() {
        val lista1 = ListaReproduccion(1, "Lista1", R.mipmap.image_logo_foreground)
        val lista2 = ListaReproduccion(2, "Lista2", R.mipmap.image_logo_foreground)
        val lista3 = ListaReproduccion(3, "Lista3", R.mipmap.image_logo_foreground)
        val lista4 = ListaReproduccion(5, "Lista4", R.mipmap.image_logo_foreground)

        val list: MutableList<ListaReproduccion> = listaListaReproduccion.toMutableList()
        list.add(lista1)
        list.add(lista2)
        list.add(lista3)
        list.add(lista4)
        listaListaReproduccion = list.toTypedArray()
    }

    class CustomAdapter(val listaListaReproduccion : Array<ListaReproduccion>, val context: Context) : BaseAdapter() {

        val layoutInflater = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layout = layoutInflater.inflate(R.layout.item_listareproduccion, parent, false)
            val listaReproduccion = listaListaReproduccion[position]

            layout.tvNombreListareproduccion.text = listaReproduccion.nombreLista
            layout.ivInicioListareproduccion.setImageResource(listaReproduccion.imagenLista)

            return layout
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return listaListaReproduccion.size
        }

    }
}