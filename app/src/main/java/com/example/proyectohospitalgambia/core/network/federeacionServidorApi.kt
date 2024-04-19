package com.example.proyectohospitalgambia.core.network

import retrofit2.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface federeacionServidorApi {
    @POST("/people/PRUEBAAND")
    fun postData(@Header("Authorization") authHeader: String, @Body body: RequestBody): Call<Void>

    @POST("/pols/ESPGNU777ORG")
    fun postDataPols(@Header("Authorization") authHeader: String, @Body body: RequestBody): Call<Void>
}