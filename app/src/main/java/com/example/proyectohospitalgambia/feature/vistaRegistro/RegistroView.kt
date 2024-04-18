package com.example.proyectohospitalgambia.feature.vistaRegistro

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
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.people.PeopleUser
import com.example.proyectohospitalgambia.feature.vistaInicio.InicioView
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

class RegistroView : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    SeekBar.OnSeekBarChangeListener {

    private lateinit var btnRegistrarUsuario: Button

    private lateinit var spinner: Spinner

    private lateinit var txtAltura: TextView

    // Variables globales para referencias a elementos de la interfaz de usuario
    private lateinit var edtNombreUsuario: EditText
    private lateinit var edtContraseniaUsuario: EditText
    private lateinit var edtContraseniaRepetirUsuario: EditText
    private lateinit var edtFechadia: EditText
    private lateinit var edtFechaMes: EditText
    private lateinit var edtFechaAnio: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var seekBar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro_view)

        // Obtener referencias a los elementos de la interfaz de usuario
        edtNombreUsuario = findViewById(R.id.edt_nombreUsuarioRegistrar)
        edtContraseniaUsuario = findViewById(R.id.edt_contraseniaUsuarioRegistrar)
        edtContraseniaRepetirUsuario = findViewById(R.id.edt_contraseniaRepetirUsuarioRegistrar)
        edtFechadia = findViewById(R.id.edt_textoDia)
        edtFechaMes = findViewById(R.id.edt_textoMes)
        edtFechaAnio = findViewById(R.id.edt_textoAnio)
        spinnerSexo = findViewById(R.id.spinnerSexo)

        seekBar = findViewById(R.id.sk_altura)

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
        //Objeto DatabaseHelper para comprobar si el usuario ya existe
        val dbHelper = DatabaseHelper(this)

        // Obtener los valores de los elementos de la interfaz de usuario
        val nombreUsuario = edtNombreUsuario.text.toString()
        val contraseniaUsuario = edtContraseniaUsuario.text.toString()
        val contraseniaRepetirUsuario = edtContraseniaRepetirUsuario.text.toString()
        val altura = seekBar.progress
        val sexo = spinnerSexo.selectedItem.toString()
        // Obtener los valores de los EditText de la fecha de nacimiento
        val dia = edtFechadia.text.toString()
        val mes = edtFechaMes.text.toString()
        val anio = edtFechaAnio.text.toString()

        // Verificar si los campos de la fecha están vacíos
        if (dia.isEmpty() || mes.isEmpty() || anio.isEmpty()) {
            // Mostrar un Toast indicando que la fecha de nacimiento debe estar completa
            Toast.makeText(this, "Por favor complete la fecha de nacimiento", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        // Formar la fecha de nacimiento en formato dd/mm/yyyy
        val fechaNacimiento = "$anio-$mes-$dia"
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaCompleta = formatoEntrada.parse(fechaNacimiento)

        val formatoSalida = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaFormateada = formatoSalida.format(fechaCompleta)

        // Verificar si todos los campos están completos
        if (nombreUsuario.isEmpty() || contraseniaUsuario.isEmpty() || contraseniaRepetirUsuario.isEmpty() ||
            altura == 0 || sexo.isEmpty()
        ) {
            // Mostrar un Toast indicando que todos los campos deben estar completos
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        } else if (!esFechaValida(dia, mes, anio)) {
            // Mostrar un Toast indicando que la fecha de nacimiento es inválida
            Toast.makeText(
                this,
                "Por favor ingrese una fecha de nacimiento válida",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (contraseniaUsuario != contraseniaRepetirUsuario) {
            // Mostrar un Toast indicando que las contraseñas no coinciden
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        // Comprobar si el usuario ya existe en la base de datos
        if (dbHelper.usuarioExiste(nombreUsuario)) {
            // Mostrar un Toast indicando que el usuario ya existe y salir del metodo
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            return false
        }


        // Crear un objeto JSON con la información recogida
        val jsonObject = JSONObject()
        val id = generarIdAleatorio()
        jsonObject.put("id", id)
        jsonObject.put("dob", fechaFormateada)  // Valor vacío para dob
        jsonObject.put("name", nombreUsuario)
        val rolesArray = JSONArray()
        jsonObject.put("roles", rolesArray)
        jsonObject.put("active", true)
        jsonObject.put("gender", sexo)
        jsonObject.put("size", altura)
        jsonObject.put("password", contraseniaUsuario)  // Valor vacío para password

        // Convertir el objeto JSON a una cadena
        val jsonString = jsonObject.toString()

        val persona = PeopleUser(id, jsonString)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtNombreUsuario.text.clear()
        edtContraseniaUsuario.text.clear()
        edtContraseniaRepetirUsuario.text.clear()
        seekBar.progress = 0
        spinnerSexo.setSelection(0)
        edtFechadia.text.clear()
        edtFechaMes.text.clear()
        edtFechaAnio.text.clear()

        // Llamar al método insertarPersona con el JSON
        return dbHelper.insertarPersona(persona)
    }

    private fun esFechaValida(dia: String, mes: String, anio: String): Boolean {
        // Verificar si el día, mes y año son números enteros válidos
        val diaInt = dia.toIntOrNull() ?: return false
        val mesInt = mes.toIntOrNull() ?: return false
        val anioInt = anio.toIntOrNull() ?: return false

        // Verificar si el mes está entre 1 y 12
        if (mesInt < 1 || mesInt > 12) {
            return false
        }

        // Verificar si el día está dentro del rango válido para el mes
        val diasPorMes = when (mesInt) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (esAnioBisiesto(anioInt)) 29 else 28
            else -> return false
        }
        if (diaInt < 1 || diaInt > diasPorMes) {
            return false
        }

        // La fecha es válida
        return true
    }

    private fun esAnioBisiesto(anio: Int): Boolean {
        // Verificar si el año es bisiesto
        return anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)
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


