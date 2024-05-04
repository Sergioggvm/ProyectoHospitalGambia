package com.example.proyectohospitalgambia.feature.vistaGraficaOsat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GraficaOsatView : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_osat_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartOsat = view.findViewById<LineChart>(R.id.graficoLineas_SaturacionOxigeno)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de osat de la base de datos
        val datosOsat = MainActivity.databaseHelper?.obtenerTodosLosDatosOsat(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de osat
        val entriesOsat = datosOsat?.mapIndexed { index, osat ->
            Entry(index.toFloat(), osat.presionSanguinea.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetOsat = LineDataSet(entriesOsat, getString(R.string.osat))
        dataSetOsat.color = Color.BLUE
        dataSetOsat.valueTextColor = Color.BLACK
        dataSetOsat.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataOsat = LineData(dataSetOsat)
        chartOsat.data = dataOsat
        chartOsat.setTouchEnabled(true)
        chartOsat.setPinchZoom(true)
        chartOsat.description.text = getString(R.string.osat)
        chartOsat.setNoDataText(getString(R.string.no_hay_datos_disponibles))
        chartOsat.invalidate()
    }

}