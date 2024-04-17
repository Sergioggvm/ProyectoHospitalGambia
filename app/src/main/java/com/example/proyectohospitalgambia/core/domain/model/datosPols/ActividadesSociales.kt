package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class ActividadesSociales(
    val fechaRealizacion: String,
    val tipoPol: String = "ActividadSociales",
    val minutosActividad : Int,
    val notas : String
)
