package com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol


/**
 * Clase NuevoRegistroServidorViewModel.
 *
 * Esta clase representa el ViewModel para la vista de nuevo registro de servidor en la aplicación.
 *
 * @property databaseHelper Helper para interactuar con la base de datos.
 *
 * @method insertarDatosEnBaseDeDatos Método para insertar los datos del formulario en la base de datos.
 * @method listarDatos Método para listar los datos de la base de datos.
 */
class NuevoRegistroServidorViewModel : ViewModel() {
    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    fun insertarDatosEnBaseDeDatos(pol: Pol): Boolean {
        return databaseHelper.insertFormData(pol)
    }

    fun listarDatos() {
        databaseHelper.listarPols()
    }
}