package com.example.proyectohospitalgambia.core.domain.model.datosPols

import java.util.Date

data class DatosTermometro(
    val fechaHora : Date,
    val tipoPol: String = "DatosTermometro",
    val temperatura : String,
    )
