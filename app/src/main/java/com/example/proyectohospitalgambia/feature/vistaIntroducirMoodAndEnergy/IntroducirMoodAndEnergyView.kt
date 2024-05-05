package com.example.proyectohospitalgambia.feature.vistaIntroducirMoodAndEnergy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Estado
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Fragment que permite al usuario introducir datos de estado de ánimo y energía.
 */
class IntroducirMoodAndEnergyView : Fragment(), SeekBar.OnSeekBarChangeListener {

    private lateinit var seekBarEstadoAnimo: SeekBar
    private lateinit var seekBarEnergia: SeekBar
    private lateinit var edtNotas: EditText
    private lateinit var imgMood: ImageView
    private lateinit var imgEnergy: ImageView

    private val viewModel: IntroducirMoodAndEnergyViewModel by viewModels()

    /**
     * Método que se llama para tener la vista del fragment inflada y lista.
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar cualquier vista en el fragment.
     * @param container Si no es nulo, esta es la vista principal a la que se debe adjuntar la UI del fragment.
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     * @return Retorna la vista del fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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


    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
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
                Toast.makeText(context, R.string.toast_datos_guardados, Toast.LENGTH_SHORT).show()

                // Generar IDs aleatorios como strings
                val idPols = generarIdAleatorio()
                val idBook =
                    MainActivity.usuario?.id.toString() // Asumiendo que MainActivity.idUsuario es un Long o un Int

                val pol = Pol(idPols, idBook, datosFormulario.toString(), "false")

                usuarioActivo?.pols?.add(pol)

                // Llamar al método del ViewModel para insertar datos
                val resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                if (resultado) {
                    // Navegar hacia atrás
                    requireActivity().supportFragmentManager.popBackStack()

                } else {
                    Toast.makeText(
                        requireContext(), R.string.toast_datos_no_guardados, Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(context, R.string.toast_complete_campos, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Método para generar un ID aleatorio.
     *
     * @return Retorna un string que representa un UUID.
     */
    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Método para obtener los datos del formulario y crear el JSON.
     *
     * @return Retorna un JSONObject que contiene los datos del formulario, o null si algún campo está vacío.
     */
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los SeekBars
        val estadoAnimo = seekBarEstadoAnimo.progress
        val energia = seekBarEnergia.progress
        val notas = edtNotas.text.toString()

        // Verificar si el campo de notas está vacío
        if (notas.isEmpty()) {
            return null // Devolver null para indicar que no se ha completado el campo de notas
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
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


    /**
     * Método que se llama cuando el progreso de la SeekBar cambia.
     *
     * @param seekBar La SeekBar cuyo progreso ha cambiado.
     * @param progress El nuevo progreso de la SeekBar.
     * @param fromUser True si el cambio de progreso fue iniciado por el usuario.
     */
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

    /**
     * Método que se llama cuando el usuario comienza a mover la SeekBar.
     *
     * @param seekBar La SeekBar que el usuario ha comenzado a mover.
     */
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    /**
     * Método que se llama cuando el usuario termina de mover la SeekBar.
     *
     * @param seekBar La SeekBar que el usuario ha terminado de mover.
     */
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}