package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class Estado.
 *
 * Esta clase representa el estado de ánimo y energía de un usuario en una fecha específica.
 *
 * @property fechaRealizacion La fecha en la que se registró el estado.
 * @property tipoPol El tipo de Pol, en este caso es "Estado".
 * @property estadoAnimo El estado de ánimo del usuario.
 * @property energia El nivel de energía del usuario.
 * @property notas Notas adicionales sobre el estado.
 */
data class Estado(
    val fechaRealizacion: String,
    val tipoPol: String = "Estado",
    val estadoAnimo: String,
    val energia: String,
    val notas: String
)
