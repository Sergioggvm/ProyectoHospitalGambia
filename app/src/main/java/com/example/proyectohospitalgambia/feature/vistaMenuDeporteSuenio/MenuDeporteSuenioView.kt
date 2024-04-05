package com.example.proyectohospitalgambia.feature.vistaMenuDeporteSuenio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R

class MenuDeporteSuenioView : Fragment() {

    private lateinit var btnDatosAerobic: ImageButton
    private lateinit var btnDatosNutricion: ImageButton
    private lateinit var btnDatosSuenio: ImageButton
    private lateinit var btnDatosSocialActivo: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val menuDeporteSuenio = inflater.inflate(R.layout.fragment_menu_deporte_suenio, container, false)

        btnDatosAerobic = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosAerobic)
        btnDatosNutricion = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosNutrition)
        btnDatosSuenio = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosSleep)
        btnDatosSocialActivo = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosSocialActivities)

        val btnGraficaAerobic = menuDeporteSuenio.findViewById<ImageButton>(R.id.imgbtn_irAGraficaAerobic)
        val btnGraficaNutricion = menuDeporteSuenio.findViewById<ImageButton>(R.id.imgbtn_irAGraficaNutrition)
        val btnGraficaSuenio = menuDeporteSuenio.findViewById<ImageButton>(R.id.imgbtn_irAGraficaSleep)
        val btnGraficaSocialActivo = menuDeporteSuenio.findViewById<ImageButton>(R.id.imgbtn_irAGraficaSocialActivities)


        btnDatosAerobic.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_physical_activity)
        }

        btnDatosNutricion.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_nutrition)
        }

        btnDatosSuenio.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_sleep)
        }

        btnDatosSocialActivo.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_social_activities)
        }


        btnGraficaAerobic.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaPhysicalActivityView)
        }

        btnGraficaNutricion.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaNutritionView)
        }

        btnGraficaSuenio.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaSleepView)
        }

        btnGraficaSocialActivo.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaSocialActivitiesView)
        }

        return menuDeporteSuenio
    }
}