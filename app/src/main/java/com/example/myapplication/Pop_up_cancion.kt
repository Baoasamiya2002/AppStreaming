package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

@Suppress("DEPRECATION")
class Pop_up_cancion() {
    var contexto: Context? = null
    var botonCancion: View? = null
    var listaOpcionesAnadirLista: Array<String>? = null
    var listaListaReproduccionid: Array<Int>? = null
    var layoutInflater:LayoutInflater? = null

    fun crearPopup() : PopupMenu{
        val popup = PopupMenu(contexto, botonCancion)
        popup.menuInflater.inflate(R.menu.popup_menu_opcion, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            if(item.title.first() == 'A'){
                val builder = crearMenuAnadir()
                val dialog = builder.create()
                dialog.show()
            } else {
                Toast.makeText(contexto,
                    "Se ha eliminado de lista de reproducción", Toast.LENGTH_SHORT).show()
            }
            true
        }
        return popup
    }

    private fun mostrarListaReproduccion(){
        listaOpcionesAnadirLista = emptyArray()
        val listaListaReproduccion = arrayOf("Lista2", "Rock&Pop")
        listaListaReproduccionid = arrayOf(8, 10)
        val list: MutableList<String> = listaOpcionesAnadirLista!!.toMutableList()
        list.add("Nueva lista de reproducción")
        listaListaReproduccion.forEach {
            list.add(it)
        }
        listaOpcionesAnadirLista = list.toTypedArray()
    }

    private fun crearMenuAnadir() : AlertDialog.Builder{
        val builder = AlertDialog.Builder(contexto,
            AlertDialog.THEME_DEVICE_DEFAULT_DARK)

        builder.setTitle("Añadir a lista de reproducción")
        mostrarListaReproduccion()

        val opciones = listaOpcionesAnadirLista
        builder.setItems(opciones) { dialog, which ->
            if(which == 0){
                val builderNuevaLista = AlertDialog.Builder(contexto,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                builderNuevaLista.setTitle("Nueva lista")

                val customLayout: View = layoutInflater!!.inflate(
                    R.layout.activity_nueva_lista, null)
                builderNuevaLista.setView(customLayout)

                builderNuevaLista.setPositiveButton("CREAR") { dialog, which ->
                    val tilNombreLista = customLayout.findViewById<TextInputLayout>(R.id.tilNombreLista)
                    Toast.makeText(contexto, tilNombreLista.getEditText()?.getText().toString(), Toast.LENGTH_SHORT).show()
                }

                val dialog = builderNuevaLista.create()
                dialog.show()
            } else {
                Toast.makeText(contexto, "You Clicked : lista con id" + (listaListaReproduccionid?.get(which-1) ?: toString()), Toast.LENGTH_SHORT).show()
            }
        }
        return builder
    }
}