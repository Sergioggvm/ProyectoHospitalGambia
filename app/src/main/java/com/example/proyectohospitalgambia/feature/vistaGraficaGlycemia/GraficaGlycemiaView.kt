package com.example.proyectohospitalgambia.feature.vistaGraficaGlycemia

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


class GraficaGlycemiaView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_glycemia_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartGlucemia = view.findViewById<LineChart>(R.id.graficoLineas_Glucosa)

        // Datos de prueba para la glucemia
        val entriesGlucemia = ArrayList<Entry>()
        entriesGlucemia.add(Entry(0f, 100f)) // Día 1
        entriesGlucemia.add(Entry(1f, 105f)) // Día 2
        entriesGlucemia.add(Entry(2f, 95f)) // Día 3
        entriesGlucemia.add(Entry(3f, 110f)) // Día 4
        entriesGlucemia.add(Entry(4f, 98f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetGlucemia = LineDataSet(entriesGlucemia, "mg/dl")
        dataSetGlucemia.color = Color.RED

        // Agregar el conjunto de datos a los datos de la línea
        val lineDataGlucemia = LineData(dataSetGlucemia)

        // Aplicar los datos al gráfico y refrescarlo
        chartGlucemia.data = lineDataGlucemia
        chartGlucemia.invalidate() // Refresca el gráfico
    }

}