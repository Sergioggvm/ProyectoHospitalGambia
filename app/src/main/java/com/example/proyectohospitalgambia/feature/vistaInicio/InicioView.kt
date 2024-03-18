package com.example.proyectohospitalgambia.feature.vistaInicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R



class InicioView : Fragment() {


    private lateinit var btnInicioSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño de la vista del menú completo
        val viewInicioSesion = inflater.inflate(R.layout.fragment_inicio_view, container, false)

        btnInicioSesion = viewInicioSesion.findViewById(R.id.btn_iniciarSesion)

        // Agrega OnClickListener al botón btnJugarLocal
        btnInicioSesion.setOnClickListener {
            // Navega al fragmento de vistaTableroView cuando se hace clic en el botón
            val navController = findNavController()
            navController.navigate(R.id.action_inicioView_to_menuPrincipalView)
        }

        // Inflate the layout for this fragment
        return viewInicioSesion
    }


}