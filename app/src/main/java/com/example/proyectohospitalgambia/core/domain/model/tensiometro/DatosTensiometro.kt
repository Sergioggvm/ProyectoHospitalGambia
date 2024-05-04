package com.example.proyectohospitalgambia.core.domain.model.tensiometro

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class DatosTensiometro.
 *
 * Esta clase representa los datos de un tensiómetro en una fecha y hora específicas.
 *
 * @property tipoPol El tipo de Pol, en este caso es "DatosTensiometro".
 * @property fechaHora La fecha y hora en la que se registraron los datos.
 * @property tensionAlta La tensión arterial alta registrada.
 * @property tensionBaja La tensión arterial baja registrada.
 * @property pulso El pulso registrado.
 * @property fechaHoraFormatted La fecha y hora formateadas en una cadena de texto.
 */
data class DatosTensiometro(
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