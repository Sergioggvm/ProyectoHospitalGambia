package com.example.proyectohospitalgambia.feature.vistaGraficaBloodPressure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import android.graphics.Color
import com.example.proyectohospitalgambia.core.domain.model.datosPols.PresionSanguinea
import com.google.gson.Gson

class GraficaBloodPressureView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grafica_blood_pressure_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartBloodPressure = view.findViewById<LineChart>(R.id.graficoLineas_PresionSanguinea)
        val chartHeartRate = view.findViewById<LineChart>(R.id.graficoLineas_RitmoCardiaco)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de presión sanguínea de la base de datos
        val datosPresionSanguinea = MainActivity.databaseHelper?.obtenerTodosLosDatosPresionSanguinea(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de presión sanguínea
        val entriesSistolico = datosPresionSanguinea?.mapIndexed { index, presionSanguinea ->
            Entry(index.toFloat(), presionSanguinea.sistolico.toFloat())
        }
        val entriesDiastolico = datosPresionSanguinea?.mapIndexed { index, presionSanguinea ->
            Entry(index.toFloat(), presionSanguinea.diastolico.toFloat())
        }

        // Crear las entradas de la gráfica de ritmo cardíaco
        val entriesHeartRate = datosPresionSanguinea?.mapIndexed { index, presionSanguinea ->
            Entry(index.toFloat(), presionSanguinea.frecuenciaCardiaca.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetSistolico = LineDataSet(entriesSistolico, "Sistolico")
        dataSetSistolico.color = Color.BLUE
        dataSetSistolico.valueTextColor = Color.BLACK
        dataSetSistolico.valueTextSize = 16f

        val dataSetDiastolico = LineDataSet(entriesDiastolico, "Diastolico")
        dataSetDiastolico.color = Color.RED
        dataSetDiastolico.valueTextColor = Color.BLACK
        dataSetDiastolico.valueTextSize = 16f

        val dataSetHeartRate = LineDataSet(entriesHeartRate, "Frecuencia Cardiaca")
        dataSetHeartRate.color = Color.GREEN
        dataSetHeartRate.valueTextColor = Color.BLACK
        dataSetHeartRate.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataBloodPressure = LineData(dataSetSistolico, dataSetDiastolico)
        chartBloodPressure.data = dataBloodPressure
        chartBloodPressure.setTouchEnabled(true)
        chartBloodPressure.setPinchZoom(true)
        chartBloodPressure.description.text = "Presión Sanguínea"
        chartBloodPressure.setNoDataText("No hay datos disponibles")
        chartBloodPressure.invalidate()

        // Crear el gráfico de ritmo cardíaco y personalizarlo
        val dataHeartRate = LineData(dataSetHeartRate)
        chartHeartRate.data = dataHeartRate
        chartHeartRate.setTouchEnabled(true)
        chartHeartRate.setPinchZoom(true)
        chartHeartRate.description.text = "Ritmo Cardiaco"
        chartHeartRate.setNoDataText("No hay datos disponibles")
        chartHeartRate.invalidate()
    }
}