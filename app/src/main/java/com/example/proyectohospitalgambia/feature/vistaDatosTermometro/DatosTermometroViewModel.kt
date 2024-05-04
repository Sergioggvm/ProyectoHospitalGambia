package com.example.proyectohospitalgambia.feature.vistaDatosTermometro

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

/**
 * Clase DatosTermometroViewModel.
 *
 * Esta clase representa el ViewModel para la vista de datos del termómetro en la aplicación.
 *
 * @property databaseHelper Ayudante de la base de datos para realizar operaciones de base de datos.
 *
 * @method insertarDatosEnBaseDeDatos Método para insertar los datos del formulario en la base de datos.
 */
class DatosTermometroViewModel : ViewModel() {
    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    /**
     * Inserta los datos del formulario en la base de datos.
     *
     * @param pol Objeto Pol que contiene los datos del formulario.
     * @return Boolean Indica si la inserción fue exitosa o no.
     */
    fun insertarDatosEnBaseDeDatos(
        pol: Pol
    ): Boolean {
        return databaseHelper.insertFormData(pol)
    }
}