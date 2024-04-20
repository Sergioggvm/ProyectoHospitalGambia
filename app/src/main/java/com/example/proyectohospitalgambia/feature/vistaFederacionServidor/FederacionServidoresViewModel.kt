package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
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

    private fun generarIdAleatorio(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val id = StringBuilder()
        for (i in 0 until 10) {
            id.append(caracteres[random.nextInt(caracteres.length)])
        }
        return id.toString()
    }

    fun insertarDatosEnServidor(polJsonString: String) {

        try{
            val idPagina = generarIdAleatorio()
            val url =  MainActivity.url + "/pols/ESPGNU777ORG/" + idPagina

            val request = Request.Builder()
                .url(url)
                .post(polJsonString.toRequestBody(jsonMediaType))
                .header("Authorization", Credentials.basic("ESPGNU777ORG", "gnusolidario"))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Error al enviar la solicitud: ${e.message}")
                    // Aquí puedes manejar el error de manera adecuada, por ejemplo, mediante LiveData
                }

                override fun onResponse(call: Call, response: Response) {
                    println("Respuesta del servidor: ${response.body?.string()}")
                    // Aquí puedes manejar la respuesta de manera adecuada, por ejemplo, mediante LiveData
                }
            })
        } catch (e: IllegalArgumentException) {
            // Captura la excepción y muestra un mensaje de error
            println("Error al construir la URL: ${e.message}")
            e.printStackTrace() // Imprime la traza de la excepción en Logcat
    }
    }
}