package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class ValorEnergetico.
 *
 * Esta clase representa el valor energético de un usuario en una fecha específica.
 *
 * @property fechaRealizacion La fecha en la que se registró el valor energético.
 * @property tipoPol El tipo de Pol, en este caso es "ValorEnergetico".
 * @property kcalManana Las calorías consumidas en la mañana.
 * @property kcalTarde Las calorías consumidas en la tarde.
 * @property kcalNoche Las calorías consumidas en la noche.
 * @property kcalTotal Las calorías totales consumidas en el día.
 * @property notas Notas adicionales sobre el valor energético del usuario.
 */
data class ValorEnergetico(
    val fechaRealizacion: String,
    val tipoPol: String = "ValorEnergetico",
    val kcalManana: Int,
    val kcalTarde: Int,
    val kcalNoche: Int,
    val kcalTotal: Int,
    val notas: String
)
