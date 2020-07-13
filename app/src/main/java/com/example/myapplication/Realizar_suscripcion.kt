package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_realizar_suscripcion.*
import org.json.JSONArray
import org.json.JSONObject


class Realizar_suscripcion : AppCompatActivity(), ResultadoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_suscripcion)
        title = "Suscripción"
        supportActionBar?.hide()
    }

    fun clickbtnSuscribete(view: View?) {
        if(validarCampos()){
            val solicitud = Solicitud(this)
            val jsonObject = JSONObject()
            jsonObject.put("Nombre", tilNombreUsuario.editText?.text.toString())
            jsonObject.put("Password", tilContrasena.editText?.text.toString())
            solicitud.solicitudPost("/usuario/registro", jsonObject, this)
        } else {
            Toast.makeText(this, resources.getString(R.string.mensajeAlternoRealizarSuscripcion),
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarCampos(): Boolean {
        if(tilNombreUsuario.editText?.text.toString().trim().isEmpty() ||
                tilContrasena.editText?.text.toString().trim().isEmpty() ||
                tilConfirmarContrasena.editText?.text.toString().trim().isEmpty()){
            return false
        }
        else if(tilContrasena.editText?.text.toString() != tilConfirmarContrasena.editText?.text.toString()){
            return false
        }
        return true
    }

    override fun getResult(respuesta: JSONObject?) {
        if (respuesta != null) {
            val intent = Intent(this, Navegacion::class.java)
            intent.putExtra("idUsuario", respuesta.getInt("id"))
            startActivity(intent)
        }
    }

    override fun getArrayResult(respuesta: JSONArray?) {
    }
}