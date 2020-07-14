package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.lista_item.view.*


class Cancion_Adapter(private val mContext: Context, private var listaCanciones: List<Cancion>,
                      private val mLayoutInflater: LayoutInflater) : ArrayAdapter<Cancion>(mContext, 0, listaCanciones) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_item, parent, false)
        val cancion = listaCanciones[position]

        layout.txtNombreCancion.text = cancion.nombreCancion
        layout.txtNombreAlbum.text = cancion.album
        //layout.imgCancion.setImageResource(cancion.imagen)
        layout.imageButton.setOnClickListener{
            val pop_up_cancion = Pop_up_cancion()
            pop_up_cancion.botonCancion = layout.imageButton
            pop_up_cancion.contexto = mContext
            pop_up_cancion.layoutInflater =  mLayoutInflater

            val popup = pop_up_cancion.crearPopup()
            popup.show()
        }
        return layout
    }
}