package com.example.myapplication

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.item_listareproduccion.view.*
import org.json.JSONArray
import org.json.JSONObject


@Suppress("UNCHECKED_CAST")
class Inicio : Fragment(), ResultadoListener {
    var listaListaReproduccion : Array<ListaReproduccion> = emptyArray()
    var listaRadioGenero : Array<ListaReproduccion> = emptyArray()

    companion object {
        fun newInstance () : Inicio = Inicio()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_inicio, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val solicitud = Solicitud(activity)
        val idUsuario = this.arguments!!.getInt("idUsuario")
        solicitud.solicitudArrayGet("/lista_reproduccion/listasByUser/$idUsuario",this)
        solicitud.solicitudArrayGet("/genero",this)

        /*gvListaReproduccion.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                val idLista: Int = listaListaReproduccion.get(i).idLista
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java).putExtra("idLista", idLista)
                )
            }*/
        /*gvRadio.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                val idLista: Int = listaRadioGenero.get(i).idLista
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java).putExtra("idLista", idLista)
                )
            }*/
    }

    private fun getRadioGenero() {
        /*val lista1 = ListaReproduccion(7, "ListaGenero1", R.mipmap.image_logo_foreground)
        val lista2 = ListaReproduccion(8, "ListaGenero2", R.mipmap.image_logo_foreground)
        val lista4 = ListaReproduccion(12, "ListaGenero4", R.mipmap.image_logo_foreground)

        val list: MutableList<ListaReproduccion> = listaRadioGenero.toMutableList()
        list.add(lista1)
        list.add(lista2)
        list.add(lista4)
        listaRadioGenero = list.toTypedArray()*/
        val caRadioGenero = activity?.let { CustomAdapter(listaRadioGenero, it) }
        gvRadio.adapter = caRadioGenero
    }

    private fun getMiListaReproduccion() {
        val caListaReproduccion = activity?.let { CustomAdapter(listaListaReproduccion, it) }
        gvListaReproduccion.adapter = caListaReproduccion
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

    override fun getResult(respuesta: JSONObject?) {
    }

    override fun getArrayResult(respuesta: JSONArray?) {
        if (respuesta != null && respuesta.length() > 0) {

            if(respuesta.getJSONObject(0).has("nombre_lista")){

                val list: MutableList<ListaReproduccion> = listaListaReproduccion.toMutableList()
                for (i in 0 until respuesta.length()) {

                    val listaReproduccion = respuesta.getJSONObject(i)
                    list.add(ListaReproduccion(listaReproduccion.getInt("id"),
                        listaReproduccion.getString("nombre_lista"), R.mipmap.image_logo_foreground))
                }
                listaListaReproduccion = list.toTypedArray()
                getMiListaReproduccion()
            } else {

                val list: MutableList<ListaReproduccion> = listaRadioGenero.toMutableList()
                for (i in 0 until respuesta.length()) {
                    
                    val listaRadio = respuesta.getJSONObject(i)
                    list.add(ListaReproduccion(listaRadio.getInt("id"),
                        listaRadio.getString("nombre_genero"), R.mipmap.image_logo_foreground))
                }
                listaRadioGenero = list.toTypedArray()
                getRadioGenero()
            }
        }
    }
}