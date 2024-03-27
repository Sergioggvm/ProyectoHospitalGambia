package com.example.proyectohospitalgambia.feature.vistaDatosTermometro

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.feature.vistaAbout.AboutView
import com.example.proyectohospitalgambia.feature.vistaAjustesConexion.AjustesConexionView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaProfile.ProfileView

class DatosTermometroView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_datos_termometro_view)

        // Obtener referencia a la barra de herramientas desde el diseño
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_Principal)

        // Inflar el menú en la barra de herramientas
        toolbar.inflateMenu(R.menu.menu_principal)

        // Configurar la barra de herramientas como la barra de soporte de la actividad
        setSupportActionBar(toolbar)
    }

    //Menú de opciones
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.mn_menu -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, MainActivity::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)

                true
            }
            R.id.mn_perfil -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, ProfileView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)

                true
            }

            R.id.mn_conexion -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, AjustesConexionView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
                true
            }

            R.id.mn_acercaDe -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, AboutView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
                true
            }

            R.id.mn_datosTensiometro -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, DatosTensiometroView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)

                true
            }

            R.id.mn_datosTermometro -> {

                true
            }

            R.id.mn_salir -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}