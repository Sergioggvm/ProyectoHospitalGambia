package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class Sueno.
 *
 * Esta clase representa el sueño de un usuario en una fecha específica.
 *
 * @property fechaRealizacion La fecha en la que se registró el sueño.
 * @property tipoPol El tipo de Pol, en este caso es "Sueno".
 * @property horasSueno Las horas de sueño del usuario.
 * @property calidadSueno La calidad del sueño del usuario.
 * @property notas Notas adicionales sobre el sueño del usuario.
 */
data class Sueno(
    val fechaRealizacion: String,
    val tipoPol: String = "Sueno",
    val horasSueno: Int,
    val calidadSueno: String,
    val notas: String
)
