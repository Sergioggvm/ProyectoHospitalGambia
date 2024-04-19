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

        val chartPeso = view.findViewById<LineChart>(R.id.graficoLineas_Peso)

        val idUsuarioActual = MainActivity.usuario?.id


        val datosPeso = MainActivity.databaseHelper?.obtenerTodosLosDatosPeso(idUsuarioActual!!)

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val entriesPeso = datosPeso?.map { dato ->
            val date = format.parse(dato.fechaRealizacion)
            val timestamp = date?.time?.toFloat() ?: 0f
            Entry(timestamp, dato.kg.toFloat())
        }

        val dataSetPeso = LineDataSet(entriesPeso, "Peso")
        dataSetPeso.color = Color.BLUE
        dataSetPeso.valueTextColor = Color.BLACK
        dataSetPeso.valueTextSize = 16f

        val dataPeso = LineData(dataSetPeso)
        chartPeso.data = dataPeso
        chartPeso.setTouchEnabled(true)
        chartPeso.setPinchZoom(true)
        chartPeso.description.text = "Peso"
        chartPeso.setNoDataText("No hay datos disponibles")

        // Aquí es donde configuramos el formateador personalizado
        chartPeso.xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

            override fun getFormattedValue(value: Float): String {
                return dateFormat.format(Date(value.toLong()))
            }
        }

        chartPeso.invalidate()
    }

}