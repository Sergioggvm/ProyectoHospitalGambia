package com.example.proyectohospitalgambia.feature.vistaInicio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.feature.vistaRegistro.RegistroView

class InicioView : AppCompatActivity() {

    // Botones en la vista
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrar: Button
    private lateinit var edt_nombreUsuarioRegistrar: EditText
    private lateinit var edt_contraseniaUsuarioRegistrar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inicio_view)

        btnIniciarSesion = findViewById(R.id.btn_registrarUsuario)
        btnRegistrar = findViewById(R.id.btn_registrar)

        edt_nombreUsuarioRegistrar = findViewById(R.id.edt_nombreUsuarioRegistrar)
        edt_contraseniaUsuarioRegistrar = findViewById(R.id.edt_contraseniaUsuarioRegistrar)

        // Agrega OnClickListener al botón btnJugarLocal
        btnIniciarSesion.setOnClickListener {

            // Obtener los valores de los EditText
            val nombreUsuario = edt_nombreUsuarioRegistrar.text.toString()
            val contraseniaUsuario = edt_contraseniaUsuarioRegistrar.text.toString()

            // Verificar las credenciales en la base de datos
            val dbHelper = DatabaseHelper(this)
            val usuarioEncontrado = dbHelper.verificarCredenciales(nombreUsuario, contraseniaUsuario)

            if (usuarioEncontrado != null) {
                Log.d("Inicio de Sesión", "Usuario encontrado. ID: $usuarioEncontrado")
                MainActivity.usuario = usuarioEncontrado
                Toast.makeText(this, "Usuario correcto", Toast.LENGTH_SHORT).show()
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, MainActivity::class.java)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
            } else {
                Log.d("Inicio de Sesión", "No se encontró ningún usuario con las credenciales proporcionadas")
                Toast.makeText(this, "Creendenciales incorrectas", Toast.LENGTH_SHORT).show()
            }

        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnRegistrar.setOnClickListener {

            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, RegistroView::class.java)

            // Iniciamos la actividad sin esperar un resultado.
            startActivity(intent)

        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // No hagas nada en el botón de retroceso
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

    }
}