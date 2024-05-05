package com.example.proyectohospitalgambia.feature.vistaGraficaMoodAndEnergy

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

/**
 * Fragment que muestra la gráfica de estado de ánimo.
 */
class GraficaMoodAndEnergyView : Fragment() {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafica_mood_and_energy_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartMood = view.findViewById<LineChart>(R.id.graficoLineas_EstadoAnimo)
        val chartEnergy = view.findViewById<LineChart>(R.id.graficoLineas_Energia)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de "Mood" y "Energy" de la base de datos

        val datosEstado = MainActivity.databaseHelper?.obtenerTodosLosDatosEstado(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de "Mood"
        val entriesMood = datosEstado?.mapIndexed { index, estado ->
            Entry(index.toFloat(), estado.estadoAnimo.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetMood = LineDataSet(entriesMood, getString(R.string.mood))
        dataSetMood.color = Color.BLUE
        dataSetMood.valueTextColor = Color.BLACK

        // Crear los datos de la línea
        val lineDataMood = LineData(dataSetMood)

        // Aplicar los datos al gráfico y refrescarlo
        chartMood.data = lineDataMood
        chartMood.invalidate() // Refresca el gráfico

        // Crear las entradas de la gráfica a partir de los datos de "Energy"
        val entriesEnergy = datosEstado?.mapIndexed { index, estado ->
            Entry(index.toFloat(), estado.energia.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetEnergy = LineDataSet(entriesEnergy, getString(R.string.energy))
        dataSetEnergy.color = Color.RED
        dataSetEnergy.valueTextColor = Color.BLACK

        // Crear los datos de la línea
        val lineDataEnergy = LineData(dataSetEnergy)

        // Aplicar los datos al gráfico y refrescarlo
        chartEnergy.data = lineDataEnergy
        chartEnergy.invalidate() // Refresca el gráfico
    }

}