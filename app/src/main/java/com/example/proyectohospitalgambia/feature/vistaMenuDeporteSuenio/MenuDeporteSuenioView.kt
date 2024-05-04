package com.example.proyectohospitalgambia.feature.vistaMenuDeporteSuenio

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

class MenuDeporteSuenioView : Fragment() {

    private lateinit var btnDatosAerobic: ImageButton
    private lateinit var btnDatosNutricion: ImageButton
    private lateinit var btnDatosSuenio: ImageButton
    private lateinit var btnDatosSocialActivo: ImageButton

    private lateinit var tvUltimoDatoAerobic: TextView
    private lateinit var tvUltimoDatoNutrition: TextView
    private lateinit var tvUltimoDatoSleep: TextView
    private lateinit var tvUltimoDatoSocialActivities: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val menuDeporteSuenio =
            inflater.inflate(R.layout.fragment_menu_deporte_suenio, container, false)

        btnDatosAerobic = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosAerobic)
        btnDatosNutricion = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosNutrition)
        btnDatosSuenio = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosSleep)
        btnDatosSocialActivo = menuDeporteSuenio.findViewById(R.id.imgbtn_irADatosSocialActivities)

        val btnGraficaAerobic =
            menuDeporteSuenio.findViewById<ShapeableImageView>(R.id.imgbtn_irAGraficaAerobic)
        val btnGraficaNutricion =
            menuDeporteSuenio.findViewById<ShapeableImageView>(R.id.imgbtn_irAGraficaNutrition)
        val btnGraficaSuenio =
            menuDeporteSuenio.findViewById<ShapeableImageView>(R.id.imgbtn_irAGraficaSleep)
        val btnGraficaSocialActivo =
            menuDeporteSuenio.findViewById<ShapeableImageView>(R.id.imgbtn_irAGraficaSocialActivities)

        tvUltimoDatoAerobic = menuDeporteSuenio.findViewById(R.id.tv_ultimoDatoAerobic)
        tvUltimoDatoNutrition = menuDeporteSuenio.findViewById(R.id.tv_ultimoDatoNutrition)
        tvUltimoDatoSleep = menuDeporteSuenio.findViewById(R.id.tv_ultimoDatoSleep)
        tvUltimoDatoSocialActivities =
            menuDeporteSuenio.findViewById(R.id.tv_ultimoDatoSocialActivities)


        btnDatosAerobic.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_physical_activity)
        }

        btnDatosNutricion.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_nutrition)
        }

        btnDatosSuenio.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_sleep)
        }

        btnDatosSocialActivo.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenno_to_introducir_social_activities)
        }


        btnGraficaAerobic.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaPhysicalActivityView)
        }

        btnGraficaNutricion.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaNutritionView)
        }

        btnGraficaSuenio.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaSleepView)
        }

        btnGraficaSocialActivo.setOnClickListener {
            findNavController().navigate(R.id.action_menu_deporte_suenio_to_graficaSocialActivitiesView)
        }

        return menuDeporteSuenio
    }

    // onresume para actualizar los datos

    override fun onResume() {
        super.onResume()
        // Actualizar los datos de la vista
        actualizarUltimoDatoAerobic()
        actualizarUltimoDatoNutrition()
        actualizarUltimoDatoSleep()
        actualizarUltimoDatoSocialActivities()
    }

    private fun actualizarUltimoDatoAerobic() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoDatoAerobic =
                MainActivity.databaseHelper?.obtenerUltimaActividadFisica(idUsuario)
            val fechaMedicion =
                ultimoDatoAerobic?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)
            val aerobico = ultimoDatoAerobic?.aerobico ?: ""

            val textoDatos = "${getString(R.string.txt_Aerobico)}: $aerobico"
            val textoFinal =
                "$textoDatos\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoAerobic.text = textoFinal
        }
    }

    private fun actualizarUltimoDatoNutrition() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoDatoNutrition =
                MainActivity.databaseHelper?.obtenerUltimoValorEnergetico(idUsuario)
            val fechaMedicion =
                ultimoDatoNutrition?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)
            val kcalTotal = ultimoDatoNutrition?.kcalTotal ?: ""

            val textoDatos = "$kcalTotal ${getString(R.string.kcal)}"
            val textoFinal =
                "$textoDatos\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoNutrition.text = textoFinal
        }
    }

    private fun actualizarUltimoDatoSleep() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoDatoSleep = MainActivity.databaseHelper?.obtenerUltimoSueno(idUsuario)
            val fechaMedicion =
                ultimoDatoSleep?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)
            val horasSueno = ultimoDatoSleep?.horasSueno ?: ""

            val textoDatos = "$horasSueno ${getString(R.string.txt_Horas)}"
            val textoFinal =
                "$textoDatos\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoSleep.text = textoFinal
        }
    }

    private fun actualizarUltimoDatoSocialActivities() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoDatoSocialActivities =
                MainActivity.databaseHelper?.obtenerUltimasActividadesSociales(idUsuario)
            val fechaMedicion = ultimoDatoSocialActivities?.fechaRealizacion
                ?: getString(R.string.txt_fecha_desconocida)
            val minutosActividad = ultimoDatoSocialActivities?.minutosActividad ?: ""

            val textoDatos = "$minutosActividad ${getString(R.string.txt_Minutos)}"
            val textoFinal =
                "$textoDatos\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoSocialActivities.text = textoFinal
        }
    }
}