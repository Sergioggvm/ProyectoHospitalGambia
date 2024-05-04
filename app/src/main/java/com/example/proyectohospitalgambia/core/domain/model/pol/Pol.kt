package com.example.proyectohospitalgambia.core.domain.model.pol

/**
 * Data class Pol.
 *
 * Esta clase representa un Pol en el sistema.
 *
 * @property idPol El identificador único del Pol.
 * @property book El libro asociado al Pol.
 * @property data Los datos del Pol.
 * @property isSubido Indica si el Pol ha sido subido o no.
 */
data class Pol(
    val idPol: String,
    val book: String,
    var data: String,
    var isSubido: String
)