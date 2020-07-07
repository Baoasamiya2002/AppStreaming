package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.lista_item.view.*


class Cancion_Adapter(private val mContext: Context, private val listaCanciones: List<Cancion>) : ArrayAdapter<Cancion>(mContext, 0, listaCanciones) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_item, parent, false)

        val cancion = listaCanciones[position]

        layout.txtNombreCancion.text = cancion.nombreCancion
        layout.txtNombreAlbum.text = cancion.album
        layout.imgCancion.setImageResource(cancion.imagen)

        return layout
    }
}