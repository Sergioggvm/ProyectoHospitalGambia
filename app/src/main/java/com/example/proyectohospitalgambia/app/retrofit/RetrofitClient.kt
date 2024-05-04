package com.example.proyectohospitalgambia.app.retrofit

import com.example.proyectohospitalgambia.app.retrofit.RetrofitClient.BASE_URL
import com.example.proyectohospitalgambia.app.retrofit.RetrofitClient.instance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto Singleton que gestiona la creación de la instancia de Retrofit.
 *
 * Este objeto es responsable de crear y proporcionar una instancia compartida de Retrofit.
 * La instancia se configura con una URL base y una fábrica de convertidores Gson.
 *
 * @property BASE_URL La URL base para la API.
 * @property instance La instancia compartida de Retrofit.
 */
object RetrofitClient {
    // URL base de la API.
    private const val BASE_URL = "http://gh1.iesjulianmarias.es:5000/"

    /**
     * Instancia de Retrofit inicializada de forma perezosa.
     *
     * Esta instancia se crea la primera vez que se accede a ella. Se configura con la
     * URL base y una fábrica de convertidores Gson.
     */
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}