package com.example.proyectohospitalgambia.feature.vistaInicio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.feature.vistaRegistro.RegistroView

class InicioView : AppCompatActivity() {

    // Botones en la vista
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inicio_view)

        btnIniciarSesion = findViewById(R.id.btn_registrarUsuario)
        btnRegistrar = findViewById(R.id.btn_registrar)

        // Agrega OnClickListener al botón btnJugarLocal
        btnIniciarSesion.setOnClickListener {

            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, MainActivity::class.java)

            // Iniciamos la actividad sin esperar un resultado.
            startActivity(intent)

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