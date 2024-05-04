package com.example.proyectohospitalgambia.feature.vistaProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.feature.vistaAbout.AboutView
import com.example.proyectohospitalgambia.feature.vistaAjustesConexion.AjustesConexionView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaDatosTermometro.DatosTermometroView
import org.json.JSONObject
import org.mindrot.jbcrypt.BCrypt

class ProfileView : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var txtAltura: TextView

    // Insertar los datos en la base de datos SQLite
    private val dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile_view)

        // Obtener referencia a la barra de herramientas desde el diseño
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_Principal)

        // Inflar el menú en la barra de herramientas
        toolbar.inflateMenu(R.menu.menu_principal)

        // Configurar la barra de herramientas como la barra de soporte de la actividad
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Obtener referencias a la SeekBar y TextView
        val seekBar: SeekBar = findViewById(R.id.sk_altura)
        txtAltura = findViewById(R.id.txt_tamanioNumero)

        // Configurar el rango de la SeekBar
        seekBar.min = 50
        seekBar.max = 230

        // Configurar el listener de la SeekBar
        seekBar.setOnSeekBarChangeListener(this)

        // Obtener referencia a los elementos de la vista
        val btnActualizarAltura = findViewById<Button>(R.id.btn_actualizarAltura)
        val skAltura = findViewById<SeekBar>(R.id.sk_altura)

        val btnActualizarContrasenias = findViewById<Button>(R.id.btn_actualizarContrasenias)
        val edtContraseniaVieja = findViewById<EditText>(R.id.edt_contraseniaActual)
        val edtContraseniaNueva = findViewById<EditText>(R.id.edt_contraseniaNueva)
        val edtContraseniaNuevaRepetir = findViewById<EditText>(R.id.edt_contraseniaNuevaRepetir)


        // Configurar el OnClickListener para el botón de actualizar altura
        btnActualizarAltura.setOnClickListener {
            // Obtener la nueva altura
            val nuevaAltura = skAltura.progress

            // Verificar si el usuario activo es nulo
            val usuarioActivo = MainActivity.usuario
            if (usuarioActivo == null) {
                Toast.makeText(this, R.string.toast_usuario_nulo, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir el JSON almacenado en el campo 'data' a un objeto JSONObject
            val jsonData = JSONObject(usuarioActivo.data)

            // Actualizar la altura en el JSON
            jsonData.put("size", nuevaAltura)

            // Actualizar el JSON en el objeto usuarioActivo
            usuarioActivo.data = jsonData.toString()

            // Llamar a la función para actualizar el usuario en la base de datos
            dbHelper.actualizarDatosPersona(usuarioActivo)

            // Mostrar un mensaje de éxito
            Toast.makeText(this, R.string.toast_altura_actualizada, Toast.LENGTH_SHORT).show()
        }

        // Configurar el OnClickListener para el botón de actualizar contraseñas
        btnActualizarContrasenias.setOnClickListener {

            // Obtener las contraseñas ingresadas
            val contraseniaVieja = edtContraseniaVieja.text.toString()
            val nuevaContrasenia = edtContraseniaNueva.text.toString()
            val nuevaContraseniaRepetir = edtContraseniaNuevaRepetir.text.toString()

            if (nuevaContrasenia.isEmpty() || nuevaContraseniaRepetir.isEmpty()) {
                // Mostrar un mensaje de error si las contraseñas no coinciden
                Toast.makeText(this, R.string.toast_contrasenas_vacias, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si las contraseñas coinciden
            if (nuevaContrasenia != nuevaContraseniaRepetir) {
                // Mostrar un mensaje de error si las contraseñas no coinciden
                Toast.makeText(this, R.string.toast_contrasenas_distintas, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener el JSON almacenado en el campo 'data' del usuario activo
            val usuarioActivo = MainActivity.usuario
            val jsonData = usuarioActivo?.data?.let { JSONObject(it) }

            // Verificar si el JSON es nulo
            if (jsonData == null) {
                // Mostrar un mensaje de error si los datos del usuario son nulos
                Toast.makeText(this, R.string.toast_datos_usuario_nulo, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si la contraseña vieja coincide
            val passwordGuardada = jsonData.optString("password", "")
            if (!BCrypt.checkpw(contraseniaVieja, passwordGuardada)) {
                // Mostrar un mensaje de error si la contraseña vieja no coincide
                Toast.makeText(this, R.string.toast_contrasena_vieja_incorrecta, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Encriptar la nueva contraseña
            val contraseniaEncriptada = BCrypt.hashpw(nuevaContrasenia, BCrypt.gensalt())

            // Actualizar la contraseña en el JSON
            jsonData.put("password", contraseniaEncriptada)

            // Actualizar el JSON en el objeto usuarioActivo
            usuarioActivo?.data = jsonData.toString()

            // Llamar a la función para actualizar el usuario en la base de datos
            dbHelper.actualizarDatosPersona(usuarioActivo)

            // Limpiar los campos de contraseña
            edtContraseniaVieja.text.clear()
            edtContraseniaNueva.text.clear()
            edtContraseniaNuevaRepetir.text.clear()

            // Mostrar un mensaje de éxito
            Toast.makeText(this, R.string.toast_contrasena_actualizada, Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoSalir() {
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

    // Implementar métodos del SeekBar.OnSeekBarChangeListener
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // Actualizar el texto del TextView con el valor de la SeekBar
        txtAltura.text = progress.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // No se requiere implementación
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // No se requiere implementación
    }
}