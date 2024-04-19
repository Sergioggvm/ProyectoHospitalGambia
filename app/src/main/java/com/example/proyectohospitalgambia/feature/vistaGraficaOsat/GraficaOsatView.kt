package com.example.proyectohospitalgambia.feature.vistaGraficaOsat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import android.graphics.Color
import com.example.proyectohospitalgambia.app.MainActivity


class GraficaOsatView : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_osat_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartOsat = view.findViewById<LineChart>(R.id.graficoLineas_SaturacionOxigeno)

        // Obtener los datos de osat de la base de datos
        val datosOsat = MainActivity.databaseHelper?.obtenerTodosLosDatosOsat()

        // Crear las entradas de la gráfica a partir de los datos de osat
        val entriesOsat = datosOsat?.mapIndexed { index, osat ->
            Entry(index.toFloat(), osat.presionSanguinea.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetOsat = LineDataSet(entriesOsat, "Osat")
        dataSetOsat.color = Color.BLUE
        dataSetOsat.valueTextColor = Color.BLACK
        dataSetOsat.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataOsat = LineData(dataSetOsat)
        chartOsat.data = dataOsat
        chartOsat.setTouchEnabled(true)
        chartOsat.setPinchZoom(true)
        chartOsat.description.text = "Osat"
        chartOsat.setNoDataText("No hay datos disponibles")
        chartOsat.invalidate()
    }

}