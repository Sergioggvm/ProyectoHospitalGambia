package com.example.proyectohospitalgambia.feature.vistaIntroducirSleep

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.proyectohospitalgambia.R

class IntroducirSleepView : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var spinnerSleepQuality: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val introducirSleep = inflater.inflate(R.layout.fragment_introducir_sleep, container, false)

        spinnerSleepQuality = introducirSleep.findViewById(R.id.spinner_sleepQuality)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.calidad_suenno, // Define este array en tu archivo strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSleepQuality.adapter = adapter
        }

        spinnerSleepQuality.onItemSelectedListener = this

        return introducirSleep
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}