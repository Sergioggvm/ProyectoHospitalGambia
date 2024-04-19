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
import com.example.proyectohospitalgambia.app.MainActivity


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

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de sueño de la base de datos

        val datosSueno = MainActivity.databaseHelper?.obtenerTodosLosSuenos(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de sueño
        val entriesSleep = datosSueno?.mapIndexed { index, sueno ->
            Entry(index.toFloat(), sueno.horasSueno.toFloat())
        }

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