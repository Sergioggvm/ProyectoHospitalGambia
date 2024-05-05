package com.example.proyectohospitalgambia.core.domain.model.people

import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

/**
 * Data class PeopleUser.
 *
 * Esta clase representa a un usuario en el sistema.
 *
 * @property id El identificador único del usuario.
 * @property data Los datos del usuario.
 * @property pols Una lista mutable de Pol, que representa los diferentes tipos de datos de salud del usuario.
 */
data class PeopleUser(
    val id: String,
    var data: String
) {
    val pols: MutableList<Pol> = mutableListOf()
}