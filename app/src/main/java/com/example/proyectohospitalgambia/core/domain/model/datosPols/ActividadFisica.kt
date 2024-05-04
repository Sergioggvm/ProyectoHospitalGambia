package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class ActividadFisica.
 *
 * Esta clase representa la actividad física realizada por un usuario.
 *
 * @property fechaRealizacion La fecha en la que se realizó la actividad física.
 * @property tipoPol El tipo de Pol, en este caso es "ActividadFisica".
 * @property aerobico Los minutos de actividad aeróbica realizada.
 * @property anaerobico Los minutos de actividad anaeróbica realizada.
 * @property pasos El número de pasos dados durante la actividad física.
 */
data class ActividadFisica(
    val fechaRealizacion: String,
    val tipoPol: String = "ActividadFisica",
    val aerobico: Int,
    val anaerobico: Int,
    val pasos: Int
)
