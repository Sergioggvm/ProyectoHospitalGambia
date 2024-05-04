package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class PresionSanguinea.
 *
 * Esta clase representa la presión sanguínea de un usuario en una fecha específica.
 *
 * @property tipoPol El tipo de Pol, en este caso es "PresionSanguinea".
 * @property fechaRealizacion La fecha en la que se registró la presión sanguínea.
 * @property sistolico La presión sanguínea sistólica del usuario.
 * @property diastolico La presión sanguínea diastólica del usuario.
 * @property frecuenciaCardiaca La frecuencia cardíaca del usuario.
 */
data class PresionSanguinea(
    val tipoPol: String = "PresionSanguinea",
    val fechaRealizacion: String,
    val sistolico: Int,
    val diastolico: Int,
    val frecuenciaCardiaca: Int
)