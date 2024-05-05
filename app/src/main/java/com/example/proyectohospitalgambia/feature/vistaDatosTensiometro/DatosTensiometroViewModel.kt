package com.example.proyectohospitalgambia.feature.vistaDatosTensiometro

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

/**
 * Clase DatosTensiometroViewModel.
 *
 * Esta clase representa el ViewModel para la vista de datos del tensiómetro en la aplicación.
 *
 * @property databaseHelper Helper para interactuar con la base de datos.
 * @method insertarDatosEnBaseDeDatos Método para insertar datos en la base de datos.
 */
class DatosTensiometroViewModel : ViewModel() {
    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    /**
     * Método para insertar datos en la base de datos.
     *
     * @param pol Los datos a insertar.
     * @return boolean Devuelve true si la inserción fue exitosa, false en caso contrario.
     */
    fun insertarDatosEnBaseDeDatos(
        pol: Pol
    ): Boolean {
        return databaseHelper.insertFormData(pol)
    }
}