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
import com.example.proyectohospitalgambia.app.MainActivity


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

        // Obtener los datos de glucemia de la base de datos
        val datosGlucemia = MainActivity.databaseHelper?.obtenerTodosLosDatosGlucemia()

        // Crear las entradas de la gráfica a partir de los datos de glucemia
        val entriesGlucemia = datosGlucemia?.mapIndexed { index, glucemia ->
            Entry(index.toFloat(), glucemia.glucosa.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetGlucemia = LineDataSet(entriesGlucemia, "Glucemia")
        dataSetGlucemia.color = Color.BLUE
        dataSetGlucemia.valueTextColor = Color.BLACK
        dataSetGlucemia.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataGlucemia = LineData(dataSetGlucemia)
        chartGlucemia.data = dataGlucemia
        chartGlucemia.setTouchEnabled(true)
        chartGlucemia.setPinchZoom(true)
        chartGlucemia.description.text = "Glucemia"
        chartGlucemia.setNoDataText("No hay datos disponibles")
        chartGlucemia.invalidate()
    }

}