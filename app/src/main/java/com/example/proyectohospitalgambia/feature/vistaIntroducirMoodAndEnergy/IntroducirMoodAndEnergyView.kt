package com.example.proyectohospitalgambia.feature.vistaIntroducirMoodAndEnergy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Estado
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.feature.vistaIntroducirGlycemia.IntroducirGlycemiaViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class IntroducirMoodAndEnergyView : Fragment(), SeekBar.OnSeekBarChangeListener {

    private lateinit var seekBarEstadoAnimo: SeekBar
    private lateinit var seekBarEnergia: SeekBar
    private lateinit var edtNotas: EditText
    private lateinit var imgMood: ImageView
    private lateinit var imgEnergy: ImageView

    private val viewModel: IntroducirMoodAndEnergyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_introducir_mood_and_energy, container, false)

        seekBarEstadoAnimo = view.findViewById(R.id.seekBar_Mood)
        seekBarEnergia = view.findViewById(R.id.seekBar_Energy)
        imgMood = view.findViewById(R.id.imgv_mood)
        imgMood.setImageResource(R.drawable.icono_mood0)


        edtNotas = view.findViewById(R.id.et_notesMoodAndEnergy)

        // Configurar el rango de la SeekBar
        seekBarEstadoAnimo.min = 0
        seekBarEstadoAnimo.max = 6

        // Configurar el listener de la SeekBar
        seekBarEstadoAnimo.setOnSeekBarChangeListener(this)

        // Obtener referencias a la SeekBar y TextView

        imgEnergy = view.findViewById(R.id.imgv_Energy)
        imgEnergy.setImageResource(R.drawable.icono_energy0)

        // Configurar el rango de la SeekBar
        seekBarEnergia.min = 0
        seekBarEnergia.max = 3

        // Configurar el listener de la SeekBar
        seekBarEnergia.setOnSeekBarChangeListener(this)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDone: FloatingActionButton = view.findViewById(R.id.btn_guardarMoodAndEnergy)

        btnDone.setOnClickListener {
            val usuarioActivo = MainActivity.usuario

            // Obtener los datos del formulario
            val datosFormulario = obtenerDatosFormulario()
            // Verificar si se obtuvieron los datos del formulario correctamente
            if (datosFormulario != null) {

                // Mostrar un mensaje de éxito
                Toast.makeText(context, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()

                // Generar IDs aleatorios como strings
                val idPols = generarIdAleatorio()
                val idBook = MainActivity.usuario?.id.toString() // Asumiendo que MainActivity.idUsuario es un Long o un Int

                val pol = Pol(idPols, idBook, datosFormulario.toString(), "false")

                if (usuarioActivo != null) {
                    usuarioActivo.pols.add(pol)
                }

                // Llamar al método del ViewModel para insertar datos
                var resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                if (resultado){

                    Toast.makeText(requireContext(), "Nuevo registro correcto", Toast.LENGTH_SHORT).show()

                    // Navegar hacia atrás
                    requireActivity().supportFragmentManager.popBackStack()

                } else {
                    Toast.makeText(requireContext(), "Error al completar el nuevo registro", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los SeekBars
        val estadoAnimo = seekBarEstadoAnimo.progress
        val energia = seekBarEnergia.progress
        val notas = edtNotas.text.toString()

        // Verificar si el campo de notas está vacío
        if (notas.isEmpty()) {
            // Mostrar un Toast indicando que el campo de notas está vacío
            Toast.makeText(context, "Por favor, complete el campo de notas", Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se ha completado el campo de notas
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
            Date()
        )

        val estado = Estado(
            fechaRealizacion = currentDateAndTime,
            estadoAnimo = estadoAnimo.toString(),
            energia = energia.toString(),
            notas = notas
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", estado.tipoPol)
        jsonObject.put("FechaInsercion", estado.fechaRealizacion)
        jsonObject.put("EstadoAnimo", estado.estadoAnimo)
        jsonObject.put("Energia", estado.energia)
        jsonObject.put("Notas", estado.notas)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        seekBarEstadoAnimo.progress = 0
        seekBarEnergia.progress = 0
        edtNotas.text.clear()

        return jsonObject
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