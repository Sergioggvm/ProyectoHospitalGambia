package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class ActividadFisica(
    val fechaRealizacion: String,
    val tipoPol: String = "ActividadFisica",
    val aerobico : Int,
    val anaerobico : Int,
    val pasos : Int
)
