package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R

class FederacionServidoresView : Fragment() {

    private lateinit var btnNuevoRegistro: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val federacionServidoresView = inflater.inflate(R.layout.fragment_federacion_servidores_view, container, false)

        btnNuevoRegistro = federacionServidoresView.findViewById(R.id.btn_nuevoRegistro)

        // Agrega OnClickListener al botón btnJugarLocal
        btnNuevoRegistro.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_federacionServidoresView_to_nuevoRegistroServidorView)
        }

        // Inflate the layout for this fragment
        return federacionServidoresView
    }


}