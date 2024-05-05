package com.example.proyectohospitalgambia.feature.vistaIntroducirSocialActivities

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

/**
 * Clase IntroducirSocialActivitiesViewModel.
 *
 * Esta clase representa el ViewModel para la vista de introducir las actividades sociales en la aplicación.
 *
 * @property databaseHelper Ayudante de la base de datos para realizar operaciones de base de datos.
 *
 * @method insertarDatosEnBaseDeDatos Método para insertar los datos del formulario en la base de datos.
 */
class IntroducirSocialActivitiesViewModel : ViewModel() {

    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    fun insertarDatosEnBaseDeDatos(
        pol: Pol
    ): Boolean {
        return databaseHelper.insertFormData(pol)
    }

}