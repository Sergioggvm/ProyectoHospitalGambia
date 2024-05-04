package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class GlucosaSangre.
 *
 * Esta clase representa la glucosa en sangre de un usuario en una fecha específica.
 *
 * @property fechaRealizacion La fecha en la que se registró la glucosa en sangre.
 * @property tipoPol El tipo de Pol, en este caso es "GlucosaSangre".
 * @property glucosa El nivel de glucosa en sangre del usuario.
 */
class GlucosaSangre(
    val tipoPol: String = "GlucosaSangre",
    val fechaRealizacion: String,
    val glucosa: Int
)