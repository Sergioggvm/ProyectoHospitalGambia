package com.example.proyectohospitalgambia.feature.vistaAjustesConexion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.feature.vistaAbout.AboutView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaDatosTermometro.DatosTermometroView
import com.example.proyectohospitalgambia.feature.vistaProfile.ProfileView

class AjustesConexionView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_ajustes_conexion_view)

        // Obtener referencia a la barra de herramientas desde el diseño
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_Principal)

        // Inflar el menú en la barra de herramientas
        toolbar.inflateMenu(R.menu.menu_principal)

        // Configurar la barra de herramientas como la barra de soporte de la actividad
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Obtener referencia a los EditText desde el diseño
        val edtProtocolo: EditText = findViewById(R.id.edt_protocolo)
        val edtHost: EditText = findViewById(R.id.edt_host)
        val edtPuertoServidor: EditText = findViewById(R.id.edt_puertoServidor)
        val edtFederacionId: EditText = findViewById(R.id.edt_federacionId)
        val edtContraseniaFederacion: EditText = findViewById(R.id.edt_contraseniaFederacion)

        // Obtener referencia al botón de prueba de conexión desde el diseño
        val btnActualizarConexion: Button = findViewById(R.id.btn_actualizarAjustesConexion)

        // Configurar el OnClickListener para el botón de prueba de conexión
        btnActualizarConexion.setOnClickListener {
            // Obtener los valores de los EditText
            val protocolo = edtProtocolo.text.toString()
            val host = edtHost.text.toString()
            val puertoServidor = edtPuertoServidor.text.toString()
            val federacionId = edtFederacionId.text.toString()
            val contraseniaFederacion = edtContraseniaFederacion.text.toString()

            // Formar la nueva URL
            val nuevaUrl = "$protocolo://$host:$puertoServidor/$federacionId"

            // Hacer lo que necesites con la nueva URL (por ejemplo, mostrarla en un TextView)
            MainActivity.url = nuevaUrl

            // Log de la nueva URL
            Log.d("MainActivity", "Nueva URL:" + MainActivity.url)
        }
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