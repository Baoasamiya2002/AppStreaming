package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class Navegacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegacion)
        supportActionBar?.hide()

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
                //R.id.nav_inicio -> selectedFragment = Buscar()
                R.id.nav_busqueda -> selectedFragment = Buscar()
                //R.id.nav_subirMusica -> selectedFragment = Buscar()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            }
            true
        }

}