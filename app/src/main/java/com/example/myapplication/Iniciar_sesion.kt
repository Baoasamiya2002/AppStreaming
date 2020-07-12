package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject


const val EXTRA_MESSAGE = "com.example.myapplication.MESSAGE"

class Iniciar_sesion : AppCompatActivity(), ResultadoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        title = "Streaming Musica"
        supportActionBar?.hide()

        val solicitud = Solicitud(this)
        solicitud.solicitudArrayGet(null,this)
        /*val jsonObject = JSONObject()
        jsonObject.put("Usuario", "loonaTUNES")
        jsonObject.put("Contrasena", "moonTHEworld")
        solicitud.solicitudPost("/iniciarSesion", jsonObject, this)*/

    }

    fun clickbtnSuscribirse(view: View?) {
        val intent = Intent(this, Realizar_suscripcion::class.java).apply {
            putExtra(EXTRA_MESSAGE, "variableAPasar")
        }
        startActivity(intent)
    }

    fun clickbtnEntrar(view: View?) {
        val intent = Intent(this, Navegacion::class.java)
        startActivity(intent)
    }

    override fun getResult(respuesta: JSONObject?) {
        println("Response is: $respuesta")
    }

    override fun getArrayResult(respuesta: JSONArray?) {
        println("Response is: $respuesta")
    }
}
