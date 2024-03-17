package com.example.proyectohospitalgambia.feature.menus.vistaMenuDeporteSuenno

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R

class menu_deporte_suenno : Fragment() {

    companion object {
        fun newInstance() = menu_deporte_suenno()
    }

    private val viewModel: MenuDeporteSuennoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu_deporte_suenno, container, false)
    }
}