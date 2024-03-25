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

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosAerobic.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_physical_activity)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosNutricion.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_nutrition)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosSuenio.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_sleep)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosSocialActivo.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_social_activities)
        }

        return menuDeporteSuenio
    }
}