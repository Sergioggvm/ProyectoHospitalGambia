package com.example.proyectohospitalgambia.feature.vistaIntroducirMoodAndEnergy

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R

class introducirMoodAndEnergy : Fragment() {

    companion object {
        fun newInstance() = introducirMoodAndEnergy()
    }

    private val viewModel: IntroducirMoodAndEnergyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_introducir_mood_and_energy, container, false)
    }
}