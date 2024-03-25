package com.example.proyectohospitalgambia.feature.vistaIntroducirMoodAndEnergy

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.example.proyectohospitalgambia.R

class IntroducirMoodAndEnergyView : Fragment(), SeekBar.OnSeekBarChangeListener {


    private val viewModel: IntroducirMoodAndEnergyViewModel by viewModels()

    private lateinit var imgMood: ImageView
    private lateinit var imgEnergy: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val introducirMoodEnergy =  inflater.inflate(R.layout.fragment_introducir_mood_and_energy, container, false)

        // Obtener referencias a la SeekBar y TextView
        val seekBarMood: SeekBar = introducirMoodEnergy.findViewById(R.id.seekBar_Mood)
        imgMood = introducirMoodEnergy.findViewById(R.id.imgv_mood)
        imgMood.setImageResource(R.drawable.icono_mood0)

        // Configurar el rango de la SeekBar
        seekBarMood.min = 0
        seekBarMood.max = 6

        // Configurar el listener de la SeekBar
        seekBarMood.setOnSeekBarChangeListener(this)

        // Obtener referencias a la SeekBar y TextView
        val seekBarEnergy: SeekBar = introducirMoodEnergy.findViewById(R.id.seekBar_Energy)
        imgEnergy = introducirMoodEnergy.findViewById(R.id.imgv_Energy)
        imgEnergy.setImageResource(R.drawable.icono_energy0)

        // Configurar el rango de la SeekBar
        seekBarEnergy.min = 0
        seekBarEnergy.max = 3

        // Configurar el listener de la SeekBar
        seekBarEnergy.setOnSeekBarChangeListener(this)

        return introducirMoodEnergy
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // Verificar si la SeekBar no es nula y el cambio proviene del usuario
        if (seekBar != null && fromUser) {
            // Cambiar la imagen según la SeekBar
            when (seekBar.id) {
                R.id.seekBar_Mood -> {
                    when (progress) {
                        0 -> imgMood.setImageResource(R.drawable.icono_mood0)
                        1 -> imgMood.setImageResource(R.drawable.icono_mood1)
                        2 -> imgMood.setImageResource(R.drawable.icono_mood2)
                        3 -> imgMood.setImageResource(R.drawable.icono_mood3)
                        4 -> imgMood.setImageResource(R.drawable.icono_mood4)
                        5 -> imgMood.setImageResource(R.drawable.icono_mood5)
                        6 -> imgMood.setImageResource(R.drawable.icono_mood6)
                    }
                }
                R.id.seekBar_Energy -> {
                    when (progress) {
                        0 -> imgEnergy.setImageResource(R.drawable.icono_energy0)
                        1 -> imgEnergy.setImageResource(R.drawable.icono_energy1)
                        2 -> imgEnergy.setImageResource(R.drawable.icono_energy2)
                        3 -> imgEnergy.setImageResource(R.drawable.icono_energy3)
                    }
                }
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

}