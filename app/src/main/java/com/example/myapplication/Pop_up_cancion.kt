package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONObject

@Suppress("DEPRECATION")
class Pop_up_cancion() : ResultadoListener {
    var idCancion: Int = 0
    var contexto: Context? = null
    var botonCancion: View? = null
    var listaOpcionesAnadirLista: Array<String>? = null
    var listaListaReproduccionid: Array<Int>? = null
    var layoutInflater:LayoutInflater? = null
    var idUsuario: ArrayList<Int> = arrayListOf<Int>()

    fun crearPopup() : PopupMenu{
        val popup = PopupMenu(contexto, botonCancion)
        popup.menuInflater.inflate(R.menu.popup_menu_opcion, popup.menu)

        if(idUsuario[1] < 0) {
            popup.menu.findItem(R.id.two).isVisible = false
        }

        popup.setOnMenuItemClickListener { item ->
            val solicitud = Solicitud(contexto)
            if(item.title.first() == 'A'){

                solicitud.solicitudArrayGet("/lista_reproduccion/listasByUser/${idUsuario[0]}",this)
            } else {

                solicitud.solicitudDelete("/cancionlista_reproduccion/eliminar/$idCancion/${idUsuario[2]}",this)
            }
            true
        }
        return popup
    }

    private fun mostrarListaReproduccion(respuesta: JSONArray){
        listaOpcionesAnadirLista = emptyArray()
        listaListaReproduccionid = emptyArray()
        val list: MutableList<String> = listaOpcionesAnadirLista!!.toMutableList()
        list.add("Nueva lista de reproducción")
        val listId: MutableList<Int> = listaListaReproduccionid!!.toMutableList()
        if(respuesta.length() > 0){
            for (i in 0 until respuesta.length()) {

                val listaReproduccion = respuesta.getJSONObject(i)
                list.add(listaReproduccion.getString("nombre_lista"))
                listId.add(listaReproduccion.getInt("id"))
            }
        }
        listaListaReproduccionid = listId.toTypedArray()
        listaOpcionesAnadirLista = list.toTypedArray()
    }

    private fun crearMenuAnadir(respuesta: JSONArray): AlertDialog.Builder{
        val builder = AlertDialog.Builder(contexto,
            AlertDialog.THEME_DEVICE_DEFAULT_DARK)

        builder.setTitle("Añadir a lista de reproducción")
        mostrarListaReproduccion(respuesta)

        val opciones = listaOpcionesAnadirLista
        builder.setItems(opciones) { dialog, which ->
            val solicitud = Solicitud(contexto)
            if(which == 0){
                val builderNuevaLista = AlertDialog.Builder(contexto,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                builderNuevaLista.setTitle("Nueva lista")

                val customLayout: View = layoutInflater!!.inflate(
                    R.layout.activity_nueva_lista, null)
                builderNuevaLista.setView(customLayout)

                val tilNombreLista = customLayout.findViewById<TextInputLayout>(R.id.tilNombreLista)

                builderNuevaLista.setPositiveButton("CREAR") { dialog, which ->
                    val jsonObject = JSONObject()
                    jsonObject.put("Nombre_lista", tilNombreLista.getEditText()?.getText().toString())
                    jsonObject.put("UsuarioId", idUsuario[0])
                    solicitud.solicitudPost("/lista_reproduccion/crearLista", jsonObject, this)
                }

                val dialog = builderNuevaLista.create()
                dialog.show()
            } else {
                val jsonObject = JSONObject()
                jsonObject.put("Lista_reproduccionId", listaListaReproduccionid?.get(which - 1))
                jsonObject.put("CancionId", idCancion)
                solicitud.solicitudPost("/cancionlista_reproduccion/crear", jsonObject, this)
            }
        }

        return builder
    }

    override fun getResult(respuesta: JSONObject?) {
        if (respuesta != null) {
            if(respuesta.getInt("id") == 0){
                Toast.makeText(contexto,
                    "Se ha eliminado de lista de reproducción", Toast.LENGTH_SHORT).show()
            }
            else if (respuesta.getInt("id") < 0) {
                Toast.makeText(contexto,
                    "Se ha agregado la canción a la lista de reproducción", Toast.LENGTH_SHORT).show()
            }
            else if (respuesta.getString("nombre_lista") == "Nueva") {
                val solicitud = Solicitud(contexto)
                val jsonObject = JSONObject()
                jsonObject.put("Lista_reproduccionId", respuesta.getInt("id"))
                jsonObject.put("CancionId", idCancion)
                solicitud.solicitudPost("/cancionlista_reproduccion/crear", jsonObject, this)
            }
        }
    }

    override fun getArrayResult(respuesta: JSONArray?) {
        if (respuesta != null && respuesta.length() > 0) {

            if(respuesta.getJSONObject(0).has("nombre_lista")){
                val builder = crearMenuAnadir(respuesta)
                val dialog = builder.create()
                dialog.show()
            }
        } else {
            val jarr = JSONArray()
            val builder = crearMenuAnadir(jarr)
            val dialog = builder.create()
            dialog.show()
        }
    }
}