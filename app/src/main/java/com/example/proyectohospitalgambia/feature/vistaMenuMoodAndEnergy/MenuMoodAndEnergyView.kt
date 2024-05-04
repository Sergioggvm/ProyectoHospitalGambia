package com.example.proyectohospitalgambia.feature.vistaMenuMoodAndEnergy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.google.android.material.imageview.ShapeableImageView

/**
 * Clase MenuMoodAndEnergyView.
 *
 * Esta clase representa la vista del menú de estado de ánimo y energía en la aplicación.
 *
 * @property btnDatosMoodAndEnergy Botón para navegar a los datos de estado de ánimo y energía.
 * @property btnGraficaMoodAndEnergy Botón para navegar a la gráfica de estado de ánimo y energía.
 * @property tvUltimoDatoMoodAndEnergy TextView para mostrar el último dato de estado de ánimo y energía.
 *
 * @method onCreateView Método que se llama para crear la vista del fragmento.
 * @method onResume Método que se llama cuando el fragmento comienza a interactuar con el usuario.
 * @method actualizarUltimoDatoMoodAndEnergy Método para actualizar el último dato de estado de ánimo y energía.
 */
class MenuMoodAndEnergyView : Fragment() {

    private lateinit var btnDatosMoodAndEnergy: ImageButton
    private lateinit var btnGraficaMoodAndEnergy: ShapeableImageView
    private lateinit var tvUltimoDatoMoodAndEnergy: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val menuMoodAndEnergy =
            inflater.inflate(R.layout.fragment_menu_mood_and_energy, container, false)

        btnDatosMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.imgbtn_irADatosMoodAndEnergy)
        btnGraficaMoodAndEnergy =
            menuMoodAndEnergy.findViewById(R.id.imgbtn_irAGraficaMoodAndEnergy)
        tvUltimoDatoMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.tv_ultimoDatoMoodAndEnergy)

        btnDatosMoodAndEnergy.setOnClickListener {
            findNavController().navigate(R.id.action_menuMoodAndEnergyView_to_introducirMoodAndEnergy)
        }

        btnGraficaMoodAndEnergy.setOnClickListener {
            findNavController().navigate(R.id.action_menuMoodAndEnergyView_to_graficoMoodAndEnergyView)
        }





        return menuMoodAndEnergy
    }

    override fun onResume() {
        super.onResume()
        actualizarUltimoDatoMoodAndEnergy()
    }

    private fun actualizarUltimoDatoMoodAndEnergy() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoDatoMoodAndEnergy =
                MainActivity.databaseHelper?.obtenerUltimoEstado(idUsuario)
            val fechaMedicion = ultimoDatoMoodAndEnergy?.fechaRealizacion
                ?: getString(R.string.txt_fecha_desconocida)

            val estadoAnimo = ultimoDatoMoodAndEnergy?.estadoAnimo?.toIntOrNull() ?: 0
            val energia = ultimoDatoMoodAndEnergy?.energia?.toIntOrNull() ?: 0

            val textoEstadoAnimo = "$estadoAnimo/6 ${getString(R.string.txt_Animo)}"
            val textoEnergia = "$energia/3 ${getString(R.string.txt_Energia)}"

            val textoFinal =
                "$textoEstadoAnimo\n$textoEnergia\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoMoodAndEnergy.text = textoFinal
        }
    }
}
