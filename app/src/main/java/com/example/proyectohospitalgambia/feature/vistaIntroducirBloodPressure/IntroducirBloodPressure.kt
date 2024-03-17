package com.example.proyectohospitalgambia.feature.vistaIntroducirBloodPressure

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R

class IntroducirBloodPressure : Fragment() {

    companion object {
        fun newInstance() = IntroducirBloodPressure()
    }

    private val viewModel: IntroducirBloodPressureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_introducir_blood_pressure, container, false)
    }
}