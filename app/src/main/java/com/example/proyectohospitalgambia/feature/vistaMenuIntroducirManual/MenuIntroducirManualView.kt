package com.example.proyectohospitalgambia.feature.vistaMenuIntroducirManual

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

    // Define los botones de gráficas
    private lateinit var btnGraficaSangre: ImageButton
    private lateinit var btnGraficaPeso: ImageButton
    private lateinit var btnGraficaGlicemia: ImageButton
    private lateinit var btnGraficaAgua: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuIntroducirDatosManual = inflater.inflate(R.layout.fragment_menu_introducir_manual, container, false)

        // Inicializa los botones de datos
        btnDatosSangre = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosBloodPressure)
        btnDatosPeso = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosWeight)
        btnDatosGlicemia = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosGlycemia)
        btnDatosAgua = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosOsat)

        // Inicializa los botones de gráficas
        btnGraficaSangre = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaBloodPressure)
        btnGraficaPeso = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaWeight)
        btnGraficaGlicemia = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaGlycemia)
        btnGraficaAgua = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaOsat)

        // Define los OnClickListener para los botones de datos
        btnDatosSangre.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirBloodPressure)
        }
        btnDatosPeso.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirWeight)
        }
        btnDatosGlicemia.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirGlycemia)
        }
        btnDatosAgua.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirOsat)
        }

        // Define los OnClickListener para los botones de gráficas
        btnGraficaSangre.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoBloodPressureView)
        }
        btnGraficaPeso.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoWeightView)
        }
        btnGraficaGlicemia.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoGlycemiaView)
        }
        btnGraficaAgua.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoOsatView)
        }

        return menuIntroducirDatosManual
    }
}