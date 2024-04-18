package com.example.proyectohospitalgambia.feature.vistaIntroducirSleep

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol

class IntroducirSleepViewModel : ViewModel() {

    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    fun insertarDatosEnBaseDeDatos(
        pol: Pol
    ): Boolean {
        return databaseHelper.insertFormData(pol)
    }

}