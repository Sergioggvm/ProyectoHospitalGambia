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
import com.example.proyectohospitalgambia.app.MainActivity

class GraficaNutritionView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grafica_nutrition_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartNutrition = view.findViewById<BarChart>(R.id.graficoBarras_Nutrition)

        val idUsuarioActual = MainActivity.usuario?.id
        val datosValorEnergetico = MainActivity.databaseHelper?.obtenerTodosLosDatosValorEnergetico(idUsuarioActual!!)

        val entriesManana = datosValorEnergetico?.mapIndexed { index, dato ->
            BarEntry(index.toFloat(), dato.kcalManana.toFloat())
        }

        val entriesTarde = datosValorEnergetico?.mapIndexed { index, dato ->
            BarEntry(index.toFloat(), dato.kcalTarde.toFloat())
        }

        val entriesNoche = datosValorEnergetico?.mapIndexed { index, dato ->
            BarEntry(index.toFloat(), dato.kcalNoche.toFloat())
        }

        val dataSetManana = BarDataSet(entriesManana, "Mañana")
        dataSetManana.color = Color.RED

        val dataSetTarde = BarDataSet(entriesTarde, "Tarde")
        dataSetTarde.color = Color.GREEN

        val dataSetNoche = BarDataSet(entriesNoche, "Noche")
        dataSetNoche.color = Color.BLUE

        val barData = BarData(dataSetManana, dataSetTarde, dataSetNoche)

        chartNutrition.data = barData
        chartNutrition.setTouchEnabled(true)
        chartNutrition.setPinchZoom(true)
        chartNutrition.description.text = "Valor Energetico"
        chartNutrition.setNoDataText("No hay datos disponibles")
        chartNutrition.invalidate()
    }
}