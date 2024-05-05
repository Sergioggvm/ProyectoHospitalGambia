package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class Osat.
 *
 * Esta clase representa la presión sanguínea de un usuario en una fecha específica.
 *
 * @property tipoPol El tipo de Pol, en este caso es "Osat".
 * @property fechaRealizacion La fecha en la que se registró la presión sanguínea.
 * @property presionSanguinea La presión sanguínea del usuario.
 */
data class Osat(
    val tipoPol: String = "Osat",
    val fechaRealizacion: String,
    val presionSanguinea: Int
)