package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class ActividadesSociales.
 *
 * Esta clase representa las actividades sociales realizadas por un usuario.
 *
 * @property fechaRealizacion La fecha en la que se realizó la actividad social.
 * @property tipoPol El tipo de Pol, en este caso es "ActividadSociales".
 * @property minutosActividad Los minutos que duró la actividad social.
 * @property notas Notas adicionales sobre la actividad social.
 */
data class ActividadesSociales(
    val fechaRealizacion: String,
    val tipoPol: String = "ActividadSociales",
    val minutosActividad: Int,
    val notas: String
)
