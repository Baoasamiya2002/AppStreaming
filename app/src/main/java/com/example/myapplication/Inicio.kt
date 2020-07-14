package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_inicio.*
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

        listaListaReproduccion = emptyArray()
        listaRadioGenero = emptyArray()

        val solicitud = Solicitud(activity)
        val idUsuario = this.arguments!!.getInt("idUsuario")
        solicitud.solicitudArrayGet("/lista_reproduccion/listasByUser/$idUsuario",this)
        solicitud.solicitudArrayGet("/genero",this)



        gvListaReproduccion.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val lista: ListaReproduccion = listaListaReproduccion.get(i)
                startActivity( Intent(activity, ListaReproduccionActivity::class.java).putExtra("lista", lista)
                    .putExtra("tipoList", 0).putExtra("idUsuario", arrayListOf(idUsuario, 0))
                )
            }
        gvRadio.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val lista: ListaReproduccion = listaRadioGenero.get(i)
                startActivity(
                    Intent(activity, ListaReproduccionActivity::class.java).putExtra("lista", lista)
                        .putExtra("tipoList", 2).putExtra("idUsuario", arrayListOf(idUsuario, -1))
                )
            }
    }

    private fun getRadioGenero() {
        val caRadioGenero = activity?.let { Lista_Adapter(listaRadioGenero, it) }
        gvRadio.adapter = caRadioGenero
    }

    private fun getMiListaReproduccion() {
        val caListaReproduccion = activity?.let { Lista_Adapter(listaListaReproduccion, it) }
        gvListaReproduccion.adapter = caListaReproduccion
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