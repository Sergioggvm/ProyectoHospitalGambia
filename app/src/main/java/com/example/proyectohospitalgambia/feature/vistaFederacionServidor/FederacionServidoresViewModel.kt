package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import androidx.lifecycle.ViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class FederacionServidoresViewModel : ViewModel() {


    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient()

    fun insertarDatosEnServidor(polJsonString: String) {
        val request = Request.Builder()
            .url("http://gh1.iesjulianmarias.es:5000/pols/ESPGNU777ORG/JHASW")
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
    }
}