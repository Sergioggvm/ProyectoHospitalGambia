package com.example.proyectohospitalgambia.feature.vistaDatosMedicosManual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R

class MenuIntroducirManualView : Fragment() {

    private lateinit var btnDatosSangre: ImageButton
    private lateinit var btnDatosPeso: ImageButton
    private lateinit var btnDatosGlicemia: ImageButton
    private lateinit var btnDatosAgua: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val menuIntroducirDatosManual = inflater.inflate(R.layout.fragment_menu_introducir_manual, container, false)

        btnDatosSangre = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosBloodPressure)
        btnDatosPeso = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosWeight)
        btnDatosGlicemia = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosGlycemia)
        btnDatosAgua = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosOsat)

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosSangre.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirBloodPressure)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosPeso.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirWeight)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosGlicemia.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirGlycemia)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnDatosAgua.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirOsat)
        }

        return menuIntroducirDatosManual
    }
}