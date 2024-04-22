package com.example.proyectohospitalgambia.feature.vistaMenuIntroducirManual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.google.android.material.imageview.ShapeableImageView

class MenuIntroducirManualView : Fragment() {

    private lateinit var btnDatosSangre: ImageButton
    private lateinit var btnDatosPeso: ImageButton
    private lateinit var btnDatosGlicemia: ImageButton
    private lateinit var btnDatosAgua: ImageButton

    // Define los botones de gráficas
    private lateinit var btnGraficaSangre: ShapeableImageView
    private lateinit var btnGraficaPeso: ShapeableImageView
    private lateinit var btnGraficaGlicemia: ShapeableImageView
    private lateinit var btnGraficaAgua: ShapeableImageView

    // Define los TextView para los últimos datos
    private lateinit var tvUltimoDatoBloodPressure: TextView
    private lateinit var tvUltimoDatoPeso: TextView
    private lateinit var tvUltimoDatoGlucosa: TextView
    private lateinit var tvUltimoDatoOsat: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuIntroducirDatosManual = inflater.inflate(R.layout.fragment_menu_introducir_manual, container, false)

        // Inicializa los botones de datos
        btnDatosSangre = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosBloodPressure)
        btnDatosPeso = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosWeight)
        btnDatosGlicemia = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosGlycemia)
        btnDatosAgua = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irADatosOsat)

        // Inicializa los botones de gráficas
        btnGraficaSangre = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaBloodPressure)
        btnGraficaPeso = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaWeight)
        btnGraficaGlicemia = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaGlycemia)
        btnGraficaAgua = menuIntroducirDatosManual.findViewById(R.id.imgbtn_irAGraficaOsat)

        // Inicializa los TextView para los últimos datos
        tvUltimoDatoBloodPressure = menuIntroducirDatosManual.findViewById(R.id.tv_ultimoDatoBloodPressure)
        tvUltimoDatoPeso = menuIntroducirDatosManual.findViewById(R.id.tv_ultimoDatoPeso)
        tvUltimoDatoGlucosa = menuIntroducirDatosManual.findViewById(R.id.tv_ultimoDatoGlucosa)
        tvUltimoDatoOsat = menuIntroducirDatosManual.findViewById(R.id.tv_ultimoDatoOsat)

        // Define los OnClickListener para los botones de datos
        btnDatosSangre.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirBloodPressure)
        }
        btnDatosPeso.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirWeight)
        }
        btnDatosGlicemia.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirGlycemia)
        }
        btnDatosAgua.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_introducirOsat)
        }

        // Define los OnClickListener para los botones de gráficas
        btnGraficaSangre.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoBloodPressureView)
        }
        btnGraficaPeso.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoWeightView)
        }
        btnGraficaGlicemia.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoGlycemiaView)
        }
        btnGraficaAgua.setOnClickListener {
            findNavController().navigate(R.id.action_menu_Introducir_Manual_to_graficoOsatView)
        }



        return menuIntroducirDatosManual
    }

    override fun onResume() {
        super.onResume()
        actualizarUltimaPresionSanguinea()
        actualizarUltimoPeso()
        actualizarUltimaGlicemia()
        actualizarUltimoOsat()
    }

    fun actualizarUltimaPresionSanguinea() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimaPresionSanguinea = MainActivity.databaseHelper?.obtenerUltimaPresionSanguinea(idUsuario)
            val fechaMedicion = ultimaPresionSanguinea?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)

            val sistolica = ultimaPresionSanguinea?.sistolico ?: ""
            val diastolica = ultimaPresionSanguinea?.diastolico ?: ""
            val frecuenciaCardiaca = ultimaPresionSanguinea?.frecuenciaCardiaca ?: ""

            val textoDatos = "${getString(R.string.txt_Sistolica)}: $sistolica ${getString(R.string.txt_Diastolica)}: $diastolica\n${getString(R.string.txt_FrecuenciaCardiaca)}: $frecuenciaCardiaca"
            val textoFinal = "$textoDatos\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoBloodPressure.text = textoFinal
        }
    }

    fun actualizarUltimoPeso() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoPeso = MainActivity.databaseHelper?.obtenerUltimoPeso(idUsuario)
            val fechaMedicion = ultimoPeso?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)

            val kg = ultimoPeso?.kg ?: ""

            val texto = "${getString(R.string.txt_Peso)}: $kg ${getString(R.string.Kg)}"
            val textoFinal = "$texto\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoPeso.text = textoFinal
        }
    }

    fun actualizarUltimaGlicemia() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimaGlicemia = MainActivity.databaseHelper?.obtenerUltimaGlucemia(idUsuario)
            val fechaMedicion = ultimaGlicemia?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)

            val glucosa = ultimaGlicemia?.glucosa ?: ""

            val texto = "${getString(R.string.txt_Glucosa)}: $glucosa mg/dL"
            val textoFinal = "$texto\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoGlucosa.text = textoFinal
        }
    }

    fun actualizarUltimoOsat() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoOsat = MainActivity.databaseHelper?.obtenerUltimoOsat(idUsuario)
            val fechaMedicion = ultimoOsat?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)

            val osat = ultimoOsat?.presionSanguinea ?: ""

            val texto = "${getString(R.string.txt_Osat)}: $osat %"
            val textoFinal = "$texto\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoOsat.text = textoFinal
        }
    }



}