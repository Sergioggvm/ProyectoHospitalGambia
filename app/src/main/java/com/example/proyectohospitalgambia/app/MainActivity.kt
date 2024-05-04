package com.example.proyectohospitalgambia.app

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.people.PeopleUser
import com.example.proyectohospitalgambia.feature.vistaAbout.AboutView
import com.example.proyectohospitalgambia.feature.vistaAjustesConexion.AjustesConexionView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaDatosTermometro.DatosTermometroView
import com.example.proyectohospitalgambia.feature.vistaProfile.ProfileView

/**
 * Clase MainActivity que extiende de AppCompatActivity.
 *
 * Esta es la actividad principal de la aplicación. Gestiona la creación de la base de datos,
 * la configuración de la barra de herramientas y la navegación del menú.
 *
 * @property databaseHelper Referencia a la clase DatabaseHelper para interactuar con la base de datos.
 * @property usuario Usuario actualmente logueado en la aplicación.
 * @property url URL base para las solicitudes de la API.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Objeto compañero que contiene propiedades comunes a todas las instancias de MainActivity.
     *
     * @property databaseHelper Referencia a la clase DatabaseHelper para interactuar con la base de datos.
     * @property usuario Usuario actualmente logueado en la aplicación.
     * @property url URL base para las solicitudes de la API.
     */
    companion object {
        var databaseHelper = null as DatabaseHelper?
        var usuario = null as PeopleUser?
        var url = "http://gh1.iesjulianmarias.es:5000"
    }

    /**
     * Método onCreate que se llama cuando se crea la actividad.
     *
     * Este método se encarga de configurar la vista, la barra de herramientas y la base de datos.
     *
     * @param savedInstanceState Bundle que contiene el estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencia a la barra de herramientas desde el diseño
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_Principal)

        // Inflar el menú en la barra de herramientas
        toolbar.inflateMenu(R.menu.menu_principal)

        // Configurar la barra de herramientas como la barra de soporte de la actividad
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Inicializa el controlador de la base de datos.
        databaseHelper = DatabaseHelper(this)
    }

    /**
     * Método que muestra un diálogo para confirmar si el usuario desea salir de la aplicación.
     */
    fun mostrarDialogoSalir() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.txt_MensajeTituloSalirAplicacion))
        builder.setMessage(getString(R.string.txt_MensajeSalirAplicacion))
        builder.setNegativeButton(getString(R.string.txt_No)) { dialog, _ ->
            // Si el usuario elige no salir, simplemente cerramos el diálogo
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.txt_Si)) { _, _ ->
            // Si el usuario elige salir, cerramos la actividad y, por lo tanto, la aplicación
            finishAffinity()
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Método que se llama para inflar el menú de opciones en la barra de herramientas.
     *
     * @param menu El menú a inflar.
     * @return Retorna true si el menú se infló correctamente, false en caso contrario.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    /**
     * Método que se llama cuando se selecciona un ítem del menú de opciones.
     *
     * Este método se encarga de navegar a la actividad correspondiente según el ítem seleccionado.
     *
     * @param item El ítem del menú que fue seleccionado.
     * @return Retorna true si la selección fue manejada, false en caso contrario.
     */
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