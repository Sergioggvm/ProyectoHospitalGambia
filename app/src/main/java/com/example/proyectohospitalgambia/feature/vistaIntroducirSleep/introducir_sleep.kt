package com.example.proyectohospitalgambia.feature.vistaIntroducirSleep

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class introducir_sleep : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var spinnerSleepQuality: Spinner


    companion object {
        fun newInstance() = introducir_sleep()
    }

    private val viewModel: IntroducirSleepViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_introducir_sleep, container, false)

        spinnerSleepQuality = view.findViewById(R.id.spinner_sleepQuality)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.calidad_suenno, // Define este array en tu archivo strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSleepQuality.adapter = adapter
        }

        spinnerSleepQuality.onItemSelectedListener = this

        return view

        //return inflater.inflate(R.layout.fragment_introducir_sleep, container, false)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Acciones cuando se selecciona un ítem del spinner
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Acciones cuando no se selecciona ningún ítem del spinner
    }
}