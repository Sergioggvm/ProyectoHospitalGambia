package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class Peso.
 *
 * Esta clase representa el peso de un usuario en una fecha específica.
 *
 * @property tipoPol El tipo de Pol, en este caso es "Peso".
 * @property fechaRealizacion La fecha en la que se registró el peso.
 * @property kg El peso del usuario en kilogramos.
 */
data class Peso(
    val tipoPol: String = "Peso",
    val fechaRealizacion: String,
    val kg: Int
)