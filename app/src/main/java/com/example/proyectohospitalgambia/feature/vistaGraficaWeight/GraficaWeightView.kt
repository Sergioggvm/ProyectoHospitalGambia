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

/**
 * Fragment que muestra la gráfica de peso.
 */
class GraficaWeightView : Fragment() {

    /**
     * Método que se llama para tener la vista del fragment inflada y lista.
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar cualquier vista en el fragment.
     * @param container Si no es nulo, esta es la vista principal a la que se debe adjuntar la UI del fragment.
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     * @return Retorna la vista del fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_weight_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartWeight = view.findViewById<LineChart>(R.id.graficoLineas_Peso)

        val idUsuarioActual = MainActivity.usuario?.id


        val datosPeso = MainActivity.databaseHelper?.obtenerTodosLosDatosPeso(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de peso
        val entriesPeso = datosPeso?.mapIndexed { index, peso ->
            Entry(index.toFloat(), peso.kg.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetPeso = LineDataSet(entriesPeso, getString(R.string.peso))
        dataSetPeso.color = Color.BLUE
        dataSetPeso.valueTextColor = Color.BLACK
        dataSetPeso.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataWeight = LineData(dataSetPeso)
        chartWeight.data = dataWeight
        chartWeight.setTouchEnabled(true)
        chartWeight.setPinchZoom(true)
        chartWeight.description.text = getString(R.string.peso)
        chartWeight.setNoDataText(getString(R.string.no_hay_datos_disponibles))
        chartWeight.invalidate()
    }

}