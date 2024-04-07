package com.example.proyectohospitalgambia.feature.vistaMenuMoodAndEnergy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R

class menuMoodAndEnergyView : Fragment() {

    private lateinit var btnDatosMoodAndEnergy: ImageButton

    // Define el botón de gráficas
    private lateinit var btnGraficaMoodAndEnergy: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuMoodAndEnergy = inflater.inflate(R.layout.fragment_menu_mood_and_energy, container, false)

        // Inicializa el botón de datos
        btnDatosMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.imgbtn_irADatosMoodAndEnergy)

        // Inicializa el botón de gráficas
        btnGraficaMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.imgbtn_irAGraficaMoodAndEnergy)

        // Define el OnClickListener para el botón de datos
        btnDatosMoodAndEnergy.setOnClickListener {
            findNavController().navigate(R.id.action_menuMoodAndEnergyView_to_introducirMoodAndEnergy)
        }

        // Define el OnClickListener para el botón de gráficas
        btnGraficaMoodAndEnergy.setOnClickListener {
            findNavController().navigate(R.id.action_menuMoodAndEnergyView_to_graficoMoodAndEnergyView)
        }

        return menuMoodAndEnergy
    }
}