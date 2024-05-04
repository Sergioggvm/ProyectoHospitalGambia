package com.example.proyectohospitalgambia.feature.vistaMenuPrincipal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R

class MenuPrincipalView : Fragment() {

    private lateinit var btnOpcionMedica: ImageButton
    private lateinit var btnOpcionServidor: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val menuPrincipalView =
            inflater.inflate(R.layout.fragment_menu_principal_view, container, false)

        btnOpcionMedica = menuPrincipalView.findViewById(R.id.btn_opcionMedica)
        btnOpcionServidor = menuPrincipalView.findViewById(R.id.btn_opcionServidor)

        // Agrega OnClickListener al botón btnJugarLocal
        btnOpcionMedica.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menuPrincipalView_to_menuOpcionesMedicasView)
        }

        // Agrega OnClickListener al botón btnJugarLocal
        btnOpcionServidor.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_menuPrincipalView_to_federacionServidoresView)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }

        return menuPrincipalView
    }

}