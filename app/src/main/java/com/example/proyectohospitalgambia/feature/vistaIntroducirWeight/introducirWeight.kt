package com.example.proyectohospitalgambia.feature.vistaIntroducirWeight

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R

class introducirWeight : Fragment() {

    companion object {
        fun newInstance() = introducirWeight()
    }

    private val viewModel: IntroducirWeightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_introducir_weight, container, false)
    }
}