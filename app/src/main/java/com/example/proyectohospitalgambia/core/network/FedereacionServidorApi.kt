package com.example.proyectohospitalgambia.core.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interfaz federeacionServidorApi.
 *
 * Esta interfaz define los métodos para hacer peticiones POST a la API del servidor.
 *
 * @method postData Método para enviar datos a la ruta "/people/PRUEBAAND".
 * @param authHeader El encabezado de autorización para la petición.
 * @param body El cuerpo de la petición.
 * @return Un objeto Call<Void> que representa la respuesta de la API.
 *
 * @method postDataPols Método para enviar datos a la ruta "/pols/ESPGNU777ORG".
 * @param authHeader El encabezado de autorización para la petición.
 * @param body El cuerpo de la petición.
 * @return Un objeto Call<Void> que representa la respuesta de la API.
 */
interface FedereacionServidorApi {
    @POST("/people/PRUEBAAND")
    fun postData(@Header("Authorization") authHeader: String, @Body body: RequestBody): Call<Void>

    @POST("/pols/ESPGNU777ORG")
    fun postDataPols(
        @Header("Authorization") authHeader: String,
        @Body body: RequestBody
    ): Call<Void>
}