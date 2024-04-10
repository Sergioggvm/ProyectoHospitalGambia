package com.example.proyectohospitalgambia.feature.vistaGraficaSocialActivities

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


class GraficaSocialActivitiesView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_social_activities_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartSocialActivities = view.findViewById<LineChart>(R.id.graficoLineas_ActividadesSociales)

        // Datos de prueba para las actividades sociales
        val entriesSocialActivities = ArrayList<Entry>()
        entriesSocialActivities.add(Entry(0f, 60f)) // Día 1
        entriesSocialActivities.add(Entry(1f, 40f)) // Día 2
        entriesSocialActivities.add(Entry(2f, 120f)) // Día 3
        entriesSocialActivities.add(Entry(3f, 80f)) // Día 4
        entriesSocialActivities.add(Entry(4f, 200f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetSocialActivities = LineDataSet(entriesSocialActivities, "minutos")
        dataSetSocialActivities.color = Color.BLUE
        dataSetSocialActivities.valueTextColor = Color.BLACK

        // Crear el gráfico y personalizarlo
        val data = LineData(dataSetSocialActivities)
        chartSocialActivities.data = data
        chartSocialActivities.setTouchEnabled(true)
        chartSocialActivities.setPinchZoom(true)
        chartSocialActivities.description.text = "Actividades sociales"
        chartSocialActivities.setNoDataText("No hay datos disponibles")
        chartSocialActivities.invalidate()
    }

}