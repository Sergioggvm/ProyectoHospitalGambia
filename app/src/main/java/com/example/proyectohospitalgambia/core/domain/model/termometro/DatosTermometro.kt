package com.example.proyectohospitalgambia.core.domain.model.termometro

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DatosTermometro(val fechaHora: Date,
                           val temperatura: Double) {

    val fechaHoraFormatted: String
        get() {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(fechaHora)
        }
}