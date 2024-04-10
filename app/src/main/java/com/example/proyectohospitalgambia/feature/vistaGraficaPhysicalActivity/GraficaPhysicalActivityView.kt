package com.example.proyectohospitalgambia.feature.vistaGraficaPhysicalActivity

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


class GraficaPhysicalActivityView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_physical_activity_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartActividadFisica = view.findViewById<LineChart>(R.id.graficoLineas_ActividadFisica)

        // Datos de prueba para la actividad aeróbica
        val entriesAerobica = ArrayList<Entry>()
        entriesAerobica.add(Entry(0f, 30f)) // Día 1
        entriesAerobica.add(Entry(1f, 45f)) // Día 2
        entriesAerobica.add(Entry(2f, 35f)) // Día 3
        entriesAerobica.add(Entry(3f, 50f)) // Día 4
        entriesAerobica.add(Entry(4f, 40f)) // Día 5

        // Datos de prueba para la actividad anaeróbica
        val entriesAnaerobica = ArrayList<Entry>()
        entriesAnaerobica.add(Entry(0f, 20f)) // Día 1
        entriesAnaerobica.add(Entry(1f, 25f)) // Día 2
        entriesAnaerobica.add(Entry(2f, 15f)) // Día 3
        entriesAnaerobica.add(Entry(3f, 30f)) // Día 4
        entriesAnaerobica.add(Entry(4f, 20f)) // Día 5

        // Crear los conjuntos de datos y personalizarlos
        val dataSetAerobica = LineDataSet(entriesAerobica, "Aeróbico")
        dataSetAerobica.color = Color.RED

        val dataSetAnaerobica = LineDataSet(entriesAnaerobica, "Anaeróbico")
        dataSetAnaerobica.color = Color.BLUE

        // Agregar los conjuntos de datos a los datos de la línea
        val lineDataActividadFisica = LineData(dataSetAerobica, dataSetAnaerobica)

        // Aplicar los datos al gráfico y refrescarlo
        chartActividadFisica.data = lineDataActividadFisica
        chartActividadFisica.invalidate() // Refresca el gráfico

        // ---------------------- Gráfico de pasos ----------------------

        val chartPasos = view.findViewById<LineChart>(R.id.graficoLineas_Pasos)

        // Datos de prueba para los pasos
        val entriesPasos = ArrayList<Entry>()
        entriesPasos.add(Entry(0f, 5000f)) // Día 1
        entriesPasos.add(Entry(1f, 7000f)) // Día 2
        entriesPasos.add(Entry(2f, 6000f)) // Día 3
        entriesPasos.add(Entry(3f, 8000f)) // Día 4
        entriesPasos.add(Entry(4f, 6500f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetPasos = LineDataSet(entriesPasos, "Pasos")
        dataSetPasos.color = Color.RED

        // Agregar el conjunto de datos a los datos de la línea
        val lineDataPasos = LineData(dataSetPasos)

        // Aplicar los datos al gráfico y refrescarlo
        chartPasos.data = lineDataPasos
        chartPasos.invalidate() // Refresca el gráfico
    }

}