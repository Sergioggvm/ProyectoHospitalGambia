package com.example.proyectohospitalgambia.feature.vistaInicio

import android.content.Context
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
import org.json.JSONObject
import org.mindrot.jbcrypt.BCrypt

class InicioView : AppCompatActivity() {

    // Botones en la vista
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrar: Button
    private lateinit var edt_nombreUsuarioRegistrar: EditText
    private lateinit var edt_contraseniaUsuarioRegistrar: EditText

    // Nombre de las SharedPreferences
    private val PREFS_NAME = "MisPreferencias"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inicio_view)

        btnIniciarSesion = findViewById(R.id.btn_registrarUsuario)
        btnRegistrar = findViewById(R.id.btn_registrar)

        edt_nombreUsuarioRegistrar = findViewById(R.id.edt_nombreUsuarioRegistrar)
        edt_contraseniaUsuarioRegistrar = findViewById(R.id.edt_contraseniaUsuarioRegistrar)

        // Recuperar el nombre de usuario de SharedPreferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val nombreUsuarioGuardado = sharedPreferences.getString("nombre_usuario", "")

        // Establecer el nombre de usuario guardado en el EditText
        edt_nombreUsuarioRegistrar.setText(nombreUsuarioGuardado)


        // Agrega OnClickListener al botón btnIniciarSesion
        btnIniciarSesion.setOnClickListener {

            // Obtener los valores de los EditText
            val nombreUsuario = edt_nombreUsuarioRegistrar.text.toString()
            val contraseniaUsuario = edt_contraseniaUsuarioRegistrar.text.toString()

            // Verificar las credenciales en la base de datos
            val dbHelper = DatabaseHelper(this)
            val usuarioEncontrado = dbHelper.verificarCredenciales(nombreUsuario, contraseniaUsuario)

            if (usuarioEncontrado != null) {
                // Obtener la contraseña almacenada del usuario encontrado en el campo "password" del JSON
                val jsonString = usuarioEncontrado.data
                val jsonObject = JSONObject(jsonString)
                val contraseniaAlmacenada = jsonObject.getString("password")

                // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
                if (BCrypt.checkpw(contraseniaUsuario, contraseniaAlmacenada)) {
                    Log.d("Inicio de Sesión", "Usuario autenticado. ID: ${usuarioEncontrado.id}")
                    MainActivity.usuario = usuarioEncontrado
                    Toast.makeText(this, R.string.toast_inicio_sesion_correcta, Toast.LENGTH_SHORT).show()

                    // Guardar el nombre de usuario en SharedPreferences
                    val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("nombre_usuario", nombreUsuario)
                    editor.apply()

                    // Creamos un Intent para iniciar MainActivity.
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("Inicio de Sesión", "Contraseña incorrecta para el usuario: $nombreUsuario")
                    Toast.makeText(this, R.string.toast_credenciales_incorrectas, Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("Inicio de Sesión", "No se encontró ningún usuario con el nombre: $nombreUsuario")
                Toast.makeText(this, R.string.toast_credenciales_incorrectas, Toast.LENGTH_SHORT).show()
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