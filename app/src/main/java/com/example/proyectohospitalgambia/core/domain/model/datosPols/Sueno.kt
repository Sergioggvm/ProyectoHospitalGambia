package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class Sueno(
    val fechaRealizacion: String,
    val tipoPol: String = "Sueno",
    val horasSueno : Int,
    val calidadSueno : String,
    val notas : String
)
