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

        val chartPeso = view.findViewById<LineChart>(R.id.graficoLineas_Peso)

        // Datos de prueba para el peso
        val entriesPeso = ArrayList<Entry>()
        entriesPeso.add(Entry(0f, 70f)) // Día 1
        entriesPeso.add(Entry(1f, 72f)) // Día 2
        entriesPeso.add(Entry(2f, 69f)) // Día 3
        entriesPeso.add(Entry(3f, 71f)) // Día 4
        entriesPeso.add(Entry(4f, 70f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetPeso = LineDataSet(entriesPeso, "kg")
        dataSetPeso.color = Color.RED

        // Agregar el conjunto de datos a los datos de la línea
        val lineDataPeso = LineData(dataSetPeso)

        // Aplicar los datos al gráfico y refrescarlo
        chartPeso.data = lineDataPeso
        chartPeso.invalidate() // Refresca el gráfico
    }

}