package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class Navegacion : AppCompatActivity() {

    var inicioFragment = Fragment()
    var buscarFragment = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegacion)
        supportActionBar?.hide()

        val mensajeLogin = intent.getIntExtra("idUsuario", 0)

        val bundle = Bundle()
        bundle.putInt("idUsuario", mensajeLogin)

        inicioFragment = Inicio()
        inicioFragment.setArguments(bundle)

        buscarFragment = Buscar()
        buscarFragment.setArguments(bundle)

        val menuNavegacion = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        menuNavegacion.setOnNavigationItemSelectedListener(navListener)
        if (savedInstanceState == null) {
            menuNavegacion.setSelectedItemId(R.id.nav_inicio)
        }
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_inicio -> selectedFragment = inicioFragment
                R.id.nav_busqueda -> selectedFragment = buscarFragment
                R.id.nav_subirMusica -> selectedFragment = SubirCancionesActivity()
                R.id.nav_reproductor -> abrirRep()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            }
            true
        }

    fun abrirRep(){
        var listaRep:ArrayList<Cancion> = ArrayList()
        val intent = Intent (this, ReproductorActivity::class.java)
        intent.putExtra("lista", listaRep)
        startActivity(intent)
    }

}