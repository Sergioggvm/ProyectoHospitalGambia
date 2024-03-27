package com.example.proyectohospitalgambia.feature.vistaRegistro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.feature.vistaInicio.InicioView

class RegistroView : AppCompatActivity(), AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private lateinit var btnRegistrarUsuario: Button

    private lateinit var spinner: Spinner

    private lateinit var txtAltura: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro_view)

        btnRegistrarUsuario = findViewById(R.id.btn_registrarUsuario)


        spinner = findViewById(R.id.spinnerSexo)

        // Configura el adaptador para el primer Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.opcion_sexo,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        // Obtener referencias a la SeekBar y TextView
        val seekBar: SeekBar = findViewById(R.id.sk_altura)
        txtAltura = findViewById(R.id.txt_tamanioNumeroRegistro)

        // Configurar el rango de la SeekBar
        seekBar.min = 50
        seekBar.max = 230

        // Configurar el listener de la SeekBar
        seekBar.setOnSeekBarChangeListener(this)

        // Agrega OnClickListener al botón btnJugarLocal
        btnRegistrarUsuario.setOnClickListener {

            // Creamos un Intent para iniciar VistaSeleccionPartida.
            val intent = Intent(this, InicioView::class.java)

            // Iniciamos la actividad sin esperar un resultado.
            startActivity(intent)

        }



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

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


