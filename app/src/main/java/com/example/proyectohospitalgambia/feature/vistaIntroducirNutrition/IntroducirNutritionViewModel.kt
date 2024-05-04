package com.example.proyectohospitalgambia.feature.vistaIntroducirNutrition

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

/**
 * ViewModel para el fragmento IntroducirNutritionView.
 * Proporciona métodos para interactuar con la base de datos.
 */
class IntroducirNutritionViewModel : ViewModel() {

    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    /**
     * Inserta los datos del formulario en la base de datos.
     *
     * @param pol El objeto Pol que contiene los datos del formulario.
     * @return Retorna true si la inserción fue exitosa, false en caso contrario.
     */
    fun insertarDatosEnBaseDeDatos(
        pol: Pol
    ): Boolean {
        return databaseHelper.insertFormData(pol)
    }
}