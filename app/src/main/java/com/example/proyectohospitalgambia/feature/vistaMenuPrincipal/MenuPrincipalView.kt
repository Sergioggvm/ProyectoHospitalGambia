package com.example.proyectohospitalgambia.feature.vistaMenuPrincipal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.proyectohospitalgambia.R


class MenuPrincipalView : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val menuPrincipalView = inflater.inflate(R.layout.fragment_menu_principal_view, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }

        return menuPrincipalView
    }

}