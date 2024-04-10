package com.example.proyectohospitalgambia.feature.vistaGraficaSleep

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


class GraficaSleepView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_sleep_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartSleep = view.findViewById<LineChart>(R.id.graficoLineas_Suenno)

        // Datos de prueba para el sueño
        val entriesSleep = ArrayList<Entry>()
        entriesSleep.add(Entry(0f, 8f)) // Día 1
        entriesSleep.add(Entry(1f, 7f)) // Día 2
        entriesSleep.add(Entry(2f, 9f)) // Día 3
        entriesSleep.add(Entry(3f, 8f)) // Día 4
        entriesSleep.add(Entry(4f, 7f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetSleep = LineDataSet(entriesSleep, "hrs")
        dataSetSleep.color = Color.BLUE
        dataSetSleep.valueTextColor = Color.BLACK
        dataSetSleep.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataSleep = LineData(dataSetSleep)
        chartSleep.data = dataSleep
        chartSleep.setTouchEnabled(true)
        chartSleep.setPinchZoom(true)
        chartSleep.description.text = "Horas de sueño"
        chartSleep.setNoDataText("No hay datos disponibles")
        chartSleep.invalidate()
    }

}