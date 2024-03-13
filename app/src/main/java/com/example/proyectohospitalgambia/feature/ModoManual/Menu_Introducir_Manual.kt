package com.example.proyectohospitalgambia.feature.ModoManual

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R

class Menu_Introducir_Manual : Fragment() {

    companion object {
        fun newInstance() = Menu_Introducir_Manual()
    }

    private val viewModel: MenuIntroducirManualViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu__introducir__manual, container, false)
    }
}