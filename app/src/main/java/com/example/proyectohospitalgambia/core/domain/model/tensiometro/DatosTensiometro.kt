package com.example.proyectohospitalgambia.core.domain.model.tensiometro

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DatosTensiometro (
    val tipoPol: String = "DatosTensiometro",
    val fechaHora: Date,
    val tensionAlta: Int,
    val tensionBaja: Byte,
    val pulso: Byte
) {
    val fechaHoraFormatted: String
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()
            )
            return sdf.format(fechaHora)
        }
}