package com.example.proyectohospitalgambia.core.domain.model.datosPols

data class LibroVida(
    val tipoPol: String = "NuevoLibro",
    val fechaRealizacion: String,
    val fechaLibro: String,
    val resumen: String,
    val dominio: String,
    val contexto: String,
    val detalles: String,
    val relevancia: String,
    val paginaPrivada: Boolean
)