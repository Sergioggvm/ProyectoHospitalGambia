package com.example.proyectohospitalgambia.core.domain.model.people

import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

data class PeopleUser (
    val id: String,
    val data: String
) {
    val pols: MutableList<Pol> = mutableListOf()
}