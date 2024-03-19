package com.example.proyectohospitalgambia.feature.vistaRegistro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.feature.vistaInicio.InicioView

class RegistroView : AppCompatActivity() {

    private lateinit var btnRegistrarUsuario: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro_view)

        btnRegistrarUsuario = findViewById(R.id.btn_registrarUsuario)

        // Agrega OnClickListener al botón btnJugarLocal
        btnRegistrarUsuario.setOnClickListener {

            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, InicioView::class.java)

            // Iniciamos la actividad sin esperar un resultado.
            startActivity(intent)

        }
    }
}