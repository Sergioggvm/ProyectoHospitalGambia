package com.example.proyectohospitalgambia.feature.vistaGraficaNutrition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import android.graphics.Color


class GraficaNutritionView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_nutrition_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartNutricion = view.findViewById<BarChart>(R.id.graficoBarras_Nutrition)

        // Datos de prueba para la nutrición
        val entriesManana = ArrayList<BarEntry>()
        entriesManana.add(BarEntry(0f, 500f)) // Día 1
        entriesManana.add(BarEntry(1f, 600f)) // Día 2
        entriesManana.add(BarEntry(2f, 550f)) // Día 3

        val entriesTarde = ArrayList<BarEntry>()
        entriesTarde.add(BarEntry(0f, 700f)) // Día 1
        entriesTarde.add(BarEntry(1f, 650f)) // Día 2
        entriesTarde.add(BarEntry(2f, 750f)) // Día 3

        val entriesNoche = ArrayList<BarEntry>()
        entriesNoche.add(BarEntry(0f, 600f)) // Día 1
        entriesNoche.add(BarEntry(1f, 700f)) // Día 2
        entriesNoche.add(BarEntry(2f, 650f)) // Día 3

        /* ESTA COMENTADO LO DEL TOTAL PORQUE NO ENTIENDO COMO SE PONE
        val entriesTotal = ArrayList<BarEntry>()
        entriesTotal.add(BarEntry(0f, 1800f)) // Día 1
        entriesTotal.add(BarEntry(1f, 1950f)) // Día 2
        entriesTotal.add(BarEntry(2f, 1950f)) // Día 3
        */


        // Crear los conjuntos de datos y personalizarlos
        val dataSetManana = BarDataSet(entriesManana, "Mañana")
        dataSetManana.color = Color.RED

        val dataSetTarde = BarDataSet(entriesTarde, "Tarde")
        dataSetTarde.color = Color.MAGENTA

        val dataSetNoche = BarDataSet(entriesNoche, "Noche")
        dataSetNoche.color = Color.BLUE

        //val dataSetTotal = BarDataSet(entriesTotal, "Total")
        //dataSetTotal.color = Color.YELLOW

        // Agregar los conjuntos de datos a los datos de la barra
        val barData = BarData(dataSetManana, dataSetTarde, dataSetNoche)

        // Aplicar los datos al gráfico y refrescarlo
        chartNutricion.data = barData
        chartNutricion.invalidate() // Refresca el gráfico
    }

}