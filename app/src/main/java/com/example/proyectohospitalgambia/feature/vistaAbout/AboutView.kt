package com.example.proyectohospitalgambia.feature.vistaAbout

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.feature.vistaAjustesConexion.AjustesConexionView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaDatosTermometro.DatosTermometroView
import com.example.proyectohospitalgambia.feature.vistaProfile.ProfileView
import org.json.JSONObject

class AboutView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_about_view)

        // Obtener referencia a la barra de herramientas desde el diseño
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_Principal)

        // Inflar el menú en la barra de herramientas
        toolbar.inflateMenu(R.menu.menu_principal)

        // Configurar la barra de herramientas como la barra de soporte de la actividad
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    private fun mostrarDialogoSalir() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.txt_MensajeTituloSalirAplicacion))
        builder.setMessage(getString(R.string.txt_MensajeSalirAplicacion))
        builder.setNegativeButton(getString(R.string.txt_No)) { dialog, which ->
            // Si el usuario elige no salir, simplemente cerramos el diálogo
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.txt_Si)) { dialog, which ->
            // Si el usuario elige salir, cerramos la actividad y, por lo tanto, la aplicación
            finishAffinity()
        }
        val dialog = builder.create()
        dialog.show()
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
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, DatosTermometroView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)

                true
            }

            R.id.mn_salir -> {

                mostrarDialogoSalir()
                true

            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}