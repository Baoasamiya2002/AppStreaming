package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_iniciar_sesion.*
import org.json.JSONArray
import org.json.JSONObject


const val EXTRA_MESSAGE = "com.example.myapplication.MESSAGE"

class Iniciar_sesion : AppCompatActivity(), ResultadoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        title = "Streaming Musica"
        supportActionBar?.hide()
    }

    fun clickbtnSuscribirse(view: View?) {
        val intent = Intent(this, Realizar_suscripcion::class.java)
        startActivity(intent)
    }

    fun clickbtnEntrar(view: View?) {
        val solicitud = Solicitud(this)
        val jsonObject = JSONObject()
        jsonObject.put("Nombre", tilUsuario.editText?.text.toString())
        jsonObject.put("Password", tilContrasena.editText?.text.toString())
        solicitud.solicitudPost("/usuario/login", jsonObject, this)
    }

    override fun getResult(respuesta: JSONObject?) {
        if (respuesta != null) {
            if(respuesta.getInt("id") > -1){
                val intent = Intent(this, Navegacion::class.java)
                intent.putExtra("idUsuario", respuesta.getInt("id"))
                startActivity(intent)
            } else {
                Toast.makeText(this, resources.getString(R.string.mensajeAlternoIniciarSesion),
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getArrayResult(respuesta: JSONArray?) {
    }
}