package com.example.proyectohospitalgambia.feature.vistaGraficaWeight

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
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class GraficaWeightView : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_weight_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartWeight = view.findViewById<LineChart>(R.id.graficoLineas_Peso)

        val idUsuarioActual = MainActivity.usuario?.id


        val datosPeso = MainActivity.databaseHelper?.obtenerTodosLosDatosPeso(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de peso
        val entriesPeso = datosPeso?.mapIndexed { index, peso ->
            Entry(index.toFloat(), peso.kg.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetPeso = LineDataSet(entriesPeso, "Peso")
        dataSetPeso.color = Color.BLUE
        dataSetPeso.valueTextColor = Color.BLACK
        dataSetPeso.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataWeight = LineData(dataSetPeso)
        chartWeight.data = dataWeight
        chartWeight.setTouchEnabled(true)
        chartWeight.setPinchZoom(true)
        chartWeight.description.text = "Peso"
        chartWeight.setNoDataText("No hay datos disponibles")
        chartWeight.invalidate()
    }

}