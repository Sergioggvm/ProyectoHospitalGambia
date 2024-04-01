package com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper

class NuevoRegistroServidorViewModel : ViewModel() {

    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    fun insertarDatosEnBaseDeDatos(
        id: String,
        book: String,
        data: String
    ): Boolean {
        return databaseHelper.insertFormData(id, book, data)
    }

    fun listarDatos(){
        databaseHelper.listarPols()
    }
}