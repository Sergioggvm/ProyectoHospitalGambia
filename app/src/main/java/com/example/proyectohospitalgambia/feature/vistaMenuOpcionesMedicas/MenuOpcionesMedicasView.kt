package com.example.proyectohospitalgambia.feature.vistaMenuOpcionesMedicas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R


class MenuOpcionesMedicasView : Fragment() {

    private lateinit var btnOpcionResultados: ImageButton
    private lateinit var btnOpcionAlimentacion: ImageButton
    private lateinit var btnOpcionEnergia: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val menuOpcionesMedicas = inflater.inflate(R.layout.fragment_menu_opciones_medicas_view, container, false)

        btnOpcionResultados = menuOpcionesMedicas.findViewById(R.id.btn_opcionResultados)
        btnOpcionAlimentacion = menuOpcionesMedicas.findViewById(R.id.btn_opcionAlimentacion)
        btnOpcionEnergia = menuOpcionesMedicas.findViewById(R.id.btn_opcionEnergia)

        // Agrega OnClickListener al botón btnJugarLocal
        btnOpcionResultados.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menuOpcionesMedicasView_to_menu_Introducir_Manual)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnOpcionAlimentacion.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menuOpcionesMedicasView_to_menu_deporte_suenno)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnOpcionEnergia.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menuOpcionesMedicasView_to_introducirMoodAndEnergy)
        }

        // Inflate the layout for this fragment
        return menuOpcionesMedicas
    }

}