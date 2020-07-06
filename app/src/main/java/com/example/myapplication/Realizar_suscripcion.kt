package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_realizar_suscripcion.*

class Realizar_suscripcion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_suscripcion)
        title = "Suscripción"
        supportActionBar?.hide()
        /*btnSuscribete*/
    }

    fun clickBtnOpcion(view: View?) {

        val popup = PopupMenu(this@Realizar_suscripcion, btnSuscribete)
        popup.menuInflater.inflate(R.menu.popup_menu_opcion, popup.menu)

        //usar esto para el aliminar y añadir a lista de reproducción
        popup.setOnMenuItemClickListener { item ->
            Toast.makeText(this@Realizar_suscripcion, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
            true
        }

        popup.show()
        //https://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog para la opción de elegir una de las listas o crear una nueva

    }
}