package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.Random

class FederacionServidoresViewModel : ViewModel() {


    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient()

    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    private fun generarIdAleatorio(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val id = StringBuilder()
        for (i in 0 until 10) {
            id.append(caracteres[random.nextInt(caracteres.length)])
        }
        return id.toString()
    }

    fun insertarDatosEnServidorAsync(polJsonString: String): Boolean {
        return runBlocking {
            try {
                val idPagina = generarIdAleatorio()
                val url = MainActivity.url + "/pols/ESPGNU777ORG/" + idPagina

                val request = Request.Builder()
                    .url(url)
                    .post(polJsonString.toRequestBody(jsonMediaType))
                    .header("Authorization", Credentials.basic("ESPGNU777ORG", "gnusolidario"))
                    .build()

                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                if (!response.isSuccessful) {
                    println("Error al enviar la solicitud: ${response.message}")
                    return@runBlocking false
                }

                val resultado = response.body?.string()
                val fallo = resultado == null || resultado.equals("null", ignoreCase = true)
                return@runBlocking !fallo
            } catch (e: IllegalArgumentException) {
                // Captura la excepción y muestra un mensaje de error
                println("Error al construir la URL: ${e.message}")
                e.printStackTrace() // Imprime la traza de la excepción en Logcat
                return@runBlocking false
            }  catch (e: Exception) {
                // Captura la excepción y muestra un mensaje de error
                println("Error al construir la URL: ${e.message}")
                e.printStackTrace() // Imprime la traza de la excepción en Logcat
                return@runBlocking false
            }
        }
    }

    fun recuperarDatos(): List<Pol> {
        return databaseHelper.obtenerPols()
    }

    fun actualizarEstadoSubidoEnBD(idPol: String, nuevoEstado: String): Boolean {
        return try {
            databaseHelper.actualizarEstadoSubido(idPol, nuevoEstado)
        } catch (e: Exception) {

            false
        }
    }

}