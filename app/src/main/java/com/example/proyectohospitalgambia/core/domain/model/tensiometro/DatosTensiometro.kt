package com.example.proyectohospitalgambia.core.domain.model.tensiometro

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DatosTensiometro (
    val fechaHora: Date,
    val tensionAlta: Int,
    val tensionBaja: Byte,
    val pulso: Byte
) {
    val fechaHoraFormatted: String
        get() {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(fechaHora)
        }
}