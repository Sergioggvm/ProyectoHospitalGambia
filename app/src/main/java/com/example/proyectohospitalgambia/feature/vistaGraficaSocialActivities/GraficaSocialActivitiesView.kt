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
import com.example.proyectohospitalgambia.app.MainActivity


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

        // Obtener los datos de las actividades sociales de la base de datos
        val datosActividadesSociales = MainActivity.databaseHelper?.obtenerTodosLosDatosActividadesSociales()

        // Crear las entradas de la gráfica a partir de los datos de las actividades sociales
        val entriesSocialActivities = datosActividadesSociales?.mapIndexed { index, actividadesSociales ->
            Entry(index.toFloat(), actividadesSociales.minutosActividad.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetSocialActivities = LineDataSet(entriesSocialActivities, "Minutos")
        dataSetSocialActivities.color = Color.BLUE
        dataSetSocialActivities.valueTextColor = Color.BLACK

        // Crear los datos de la línea
        val lineDataSocialActivities = LineData(dataSetSocialActivities)

        // Aplicar los datos al gráfico y refrescarlo
        chartSocialActivities.data = lineDataSocialActivities
        chartSocialActivities.invalidate() // Refresca el gráfico
    }

}