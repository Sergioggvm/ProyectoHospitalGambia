package com.example.proyectohospitalgambia.core.domain.model.datosPols

/**
 * Data class LibroVida.
 *
 * Esta clase representa un libro de vida de un usuario.
 *
 * @property tipoPol El tipo de Pol, en este caso es "NuevoLibro".
 * @property fechaRealizacion La fecha en la que se creó el libro de vida.
 * @property fechaLibro La fecha del libro de vida.
 * @property resumen Un resumen del libro de vida.
 * @property dominio El dominio del libro de vida.
 * @property contexto El contexto del libro de vida.
 * @property detalles Los detalles del libro de vida.
 * @property relevancia La relevancia del libro de vida.
 * @property paginaPrivada Si la página del libro de vida es privada o no.
 */
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