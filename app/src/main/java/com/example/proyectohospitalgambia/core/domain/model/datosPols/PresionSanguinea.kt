package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class PresionSanguinea (
    val tipoPol: String = "PresionSanguinea",
    val fechaRealizacion: String,
    val sistolico: Int,
    val diastolico: Int,
    val frecuenciaCardiaca: Int
)