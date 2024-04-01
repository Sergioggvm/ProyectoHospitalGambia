package com.example.proyectohospitalgambia.feature.vistaRegistro

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.feature.vistaInicio.InicioView
import org.json.JSONArray
import org.json.JSONObject
import java.util.Random

class RegistroView : AppCompatActivity(), AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private lateinit var btnRegistrarUsuario: Button

    private lateinit var spinner: Spinner

    private lateinit var txtAltura: TextView

    // Variables globales para referencias a elementos de la interfaz de usuario
    private lateinit var edtNombreUsuario: EditText
    private lateinit var edtContraseniaUsuario: EditText
    private lateinit var edtContraseniaRepetirUsuario: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var seekBar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro_view)

        // Obtener referencias a los elementos de la interfaz de usuario
        edtNombreUsuario = findViewById<EditText>(R.id.edt_nombreUsuarioRegistrar)
        edtContraseniaUsuario = findViewById<EditText>(R.id.edt_contraseniaUsuarioRegistrar)
        edtContraseniaRepetirUsuario = findViewById<EditText>(R.id.edt_contraseniaRepetirUsuarioRegistrar)
        spinnerSexo = findViewById<Spinner>(R.id.spinnerSexo)

        seekBar = findViewById<SeekBar>(R.id.sk_altura)

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
        txtAltura = findViewById(R.id.txt_tamanioNumeroRegistro)

        // Configurar el rango de la SeekBar
        seekBar.min = 50
        seekBar.max = 230

        // Configurar el listener de la SeekBar
        seekBar.setOnSeekBarChangeListener(this)

        // Agrega OnClickListener al botón btnJugarLocal
        btnRegistrarUsuario.setOnClickListener {

            val registroExitoso = registrarUsuario()
            if (registroExitoso) {

                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, InicioView::class.java)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }

        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val dbHelper = DatabaseHelper(this)
            dbHelper.listarUsuarios()
        }

    }

    private fun generarIdAleatorio(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val id = StringBuilder()
        for (i in 0 until 10) {
            id.append(caracteres[random.nextInt(caracteres.length)])
        }
        return id.toString()
    }

    private fun registrarUsuario(): Boolean {
        // Obtener los valores de los elementos de la interfaz de usuario
        val nombreUsuario = edtNombreUsuario.text.toString()
        val contraseniaUsuario = edtContraseniaUsuario.text.toString()
        val contraseniaRepetirUsuario = edtContraseniaRepetirUsuario.text.toString()
        val altura = seekBar.progress
        val sexo = spinnerSexo.selectedItem.toString()

        // Crear un objeto JSON con la información recogida
        val jsonObject = JSONObject()
        val id = generarIdAleatorio()
        jsonObject.put("id", id)
        jsonObject.put("dob", "")  // Valor vacío para dob
        jsonObject.put("name", nombreUsuario)
        val rolesArray = JSONArray()
        rolesArray.put("end_user")
        rolesArray.put("health_professional")
        jsonObject.put("roles", rolesArray)
        jsonObject.put("active", true)
        jsonObject.put("gender", sexo)
        jsonObject.put("lastname", "")  // Valor vacío para lastname
        jsonObject.put("password", contraseniaUsuario)  // Valor vacío para password
        jsonObject.put("education", "")  // Valor vacío para education
        jsonObject.put("ethnicity", "")  // Valor vacío para ethnicity
        jsonObject.put("profession", "")  // Valor vacío para profession
        jsonObject.put("marital_status", "")  // Valor vacío para marital_status

        // Convertir el objeto JSON a una cadena
        val jsonString = jsonObject.toString()

        // Insertar los datos en la base de datos SQLite
        val dbHelper = DatabaseHelper(this)

        // Llamar al método insertarPersona con el JSON
        return dbHelper.insertarPersona(id, jsonString)
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


