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

        // Datos de prueba para la saturación de oxígeno
        val entriesOsat = ArrayList<Entry>()
        entriesOsat.add(Entry(0f, 98f)) // Día 1
        entriesOsat.add(Entry(1f, 97f)) // Día 2
        entriesOsat.add(Entry(2f, 99f)) // Día 3
        entriesOsat.add(Entry(3f, 98f)) // Día 4
        entriesOsat.add(Entry(4f, 97f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetOsat = LineDataSet(entriesOsat, "pct")
        dataSetOsat.color = Color.RED

        // Agregar el conjunto de datos a los datos de la línea
        val lineDataOsat = LineData(dataSetOsat)

        // Aplicar los datos al gráfico y refrescarlo
        chartOsat.data = lineDataOsat
        chartOsat.invalidate() // Refresca el gráfico
    }

}