package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class ValorEnergetico(
    val fechaRealizacion: String,
    val tipoPol: String = "ValorEnergetico",
    val kcalManana : Int,
    val kcalTarde : Int,
    val kcalNoche : Int,
    val kcalTotal : Int,
    val notas : String
)
