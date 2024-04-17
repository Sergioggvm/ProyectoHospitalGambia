package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class Estado(
    val fechaRealizacion: String,
    val tipoPol: String = "Estado",
    val estadoAnimo : String,
    val energia : String,
    val notas : String
)
