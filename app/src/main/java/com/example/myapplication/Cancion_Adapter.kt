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
    var listaCancionesOriginal: List<Cancion> = listaCanciones

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.lista_item, parent, false)
        println("")
        println("")
        println("----------------------------------")
        println(listaCanciones.size);
        val cancion = listaCanciones[position]

        layout.txtNombreCancion.text = cancion.toString()
        layout.txtNombreAlbum.text = cancion.album
        layout.imgCancion.setImageResource(cancion.imagen)
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

    /*override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listaCanciones = results.values as List<Cancion> // has the filtered values
                notifyDataSetChanged() // notifies the data with new filtered values
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
            //fun filter(constraint: CharSequence){
                var constraint: CharSequence? = constraint
                val results = FilterResults() // Holds the results of a filtering operation in values
                val FilteredArrList: MutableList<Cancion> = ArrayList()
                /*if (listaCanciones == null) {
                    listaCanciones = ArrayList<String>(arrayList) // saves the original data in mOriginalValues
                }*/
                /********
                 *
                 * If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 * else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 */
                if (constraint == null || constraint.length == 0) {

                    // set the Original result to return
                    results.count = listaCancionesOriginal.size
                    results.values = listaCancionesOriginal
                } else {
                    constraint = constraint.toString().toLowerCase()
                    for (cancion in listaCancionesOriginal) {
                        if (cancion.toString().toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(cancion)
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size
                    results.values = FilteredArrList
                }
                return results
            }
        }
    }*/
}