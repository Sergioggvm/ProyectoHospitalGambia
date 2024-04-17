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
import com.example.proyectohospitalgambia.feature.vistaAbout.AboutView
import com.example.proyectohospitalgambia.feature.vistaAjustesConexion.AjustesConexionView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaDatosTermometro.DatosTermometroView
import org.json.JSONObject

class ProfileView : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var txtAltura: TextView

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
        val edtContraseniaNueva = findViewById<EditText>(R.id.edt_contraseniaNueva)
        val edtContraseniaNuevaRepetir = findViewById<EditText>(R.id.edt_contraseniaNuevaRepetir)

        // Obtener el usuario activo
        val usuarioActivo = MainActivity.usuario

        // Configurar el OnClickListener para el botón de actualizar altura
        btnActualizarAltura.setOnClickListener {
            // Obtener la nueva altura
            val nuevaAltura = skAltura.progress

            // Convertir el JSON almacenado en el campo 'data' a un objeto JSONObject
            val jsonData = JSONObject(usuarioActivo?.data)

            jsonData.put("size", nuevaAltura)


            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Altura actualizada correctamente", Toast.LENGTH_SHORT).show()
        }

        // Configurar el OnClickListener para el botón de actualizar contraseñas
        btnActualizarContrasenias.setOnClickListener {
            // Obtener la nueva contraseña
            val nuevaContrasenia = edtContraseniaNueva.text.toString()
            val nuevaContraseniaRepetir = edtContraseniaNuevaRepetir.text.toString()

            // Verificar si las contraseñas coinciden
            if (nuevaContrasenia != nuevaContraseniaRepetir) {
                // Mostrar un mensaje de error si las contraseñas no coinciden
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir el JSON almacenado en el campo 'data' a un objeto JSONObject
            val jsonData = JSONObject(usuarioActivo?.data)
            Log.d("UserData", usuarioActivo?.data.toString())

            // Verificar si el campo 'password' debe ser actualizado
            if (nuevaContrasenia.isNotEmpty()) {
                jsonData.put("password", nuevaContrasenia)
                Log.d("UserData", "prueba")
            }

            // Limpiar los campos de contraseña
            edtContraseniaNueva.text.clear()
            edtContraseniaNuevaRepetir.text.clear()

            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
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