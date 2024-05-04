package com.example.proyectohospitalgambia.feature.vistaGraficaNutrition

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

/**
 * Fragment que muestra la gráfica de nutrición.
 */
class GraficaNutritionView : Fragment() {

    /**
     * Método que se llama para tener la vista del fragment inflada y lista.
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar cualquier vista en el fragment.
     * @param container Si no es nulo, esta es la vista principal a la que se debe adjuntar la UI del fragment.
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     * @return Retorna la vista del fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grafica_nutrition_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartNutrition = view.findViewById<BarChart>(R.id.graficoBarras_Nutrition)

        val idUsuarioActual = MainActivity.usuario?.id
        val datosValorEnergetico =
            MainActivity.databaseHelper?.obtenerTodosLosDatosValorEnergetico(idUsuarioActual!!)

        val entriesManana = datosValorEnergetico?.mapIndexed { index, dato ->
            BarEntry(index.toFloat(), dato.kcalManana.toFloat())
        }

        val entriesTarde = datosValorEnergetico?.mapIndexed { index, dato ->
            BarEntry(index.toFloat(), dato.kcalTarde.toFloat())
        }

        val entriesNoche = datosValorEnergetico?.mapIndexed { index, dato ->
            BarEntry(index.toFloat(), dato.kcalNoche.toFloat())
        }

        val dataSetManana = BarDataSet(entriesManana, getString(R.string.manana))
        dataSetManana.color = Color.RED

        val dataSetTarde = BarDataSet(entriesTarde, getString(R.string.tarde))
        dataSetTarde.color = Color.GREEN

        val dataSetNoche = BarDataSet(entriesNoche, getString(R.string.noche))
        dataSetNoche.color = Color.BLUE

        val barData = BarData(dataSetManana, dataSetTarde, dataSetNoche)

        chartNutrition.data = barData
        chartNutrition.setTouchEnabled(true)
        chartNutrition.setPinchZoom(true)
        chartNutrition.description.text = getString(R.string.valor_energetico)
        chartNutrition.setNoDataText(getString(R.string.no_hay_datos_disponibles))
        chartNutrition.invalidate()
    }
}