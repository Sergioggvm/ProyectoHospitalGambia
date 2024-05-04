package com.example.proyectohospitalgambia.core.domain.model.termometro

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class DatosTermometro.
 *
 * Esta clase representa los datos de un termómetro en una fecha y hora específicas.
 *
 * @property fechaHora La fecha y hora en la que se registraron los datos.
 * @property temperatura La temperatura registrada.
 * @property fechaHoraFormatted La fecha y hora formateadas en una cadena de texto.
 */
data class DatosTermometro(
    val fechaHora: Date, val temperatura: Double
) {

    val fechaHoraFormatted: String
        get() {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(fechaHora)
        }
}