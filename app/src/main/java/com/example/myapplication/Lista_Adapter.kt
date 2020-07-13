package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_listareproduccion.view.*

class Lista_Adapter(val listaListaReproduccion : Array<ListaReproduccion>, val context: Context) : BaseAdapter() {

    val layoutInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layout = layoutInflater.inflate(R.layout.item_listareproduccion, parent, false)
        val listaReproduccion = listaListaReproduccion[position]

        layout.tvNombreListareproduccion.text = listaReproduccion.nombreLista
        layout.ivInicioListareproduccion.setImageResource(listaReproduccion.imagenLista)

        return layout
    }

    override fun getItem(position: Int): Any? {
        return listaListaReproduccion.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listaListaReproduccion.size
    }

}