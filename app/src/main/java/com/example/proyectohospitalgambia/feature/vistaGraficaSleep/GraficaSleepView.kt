package com.example.proyectohospitalgambia.feature.vistaGraficaSleep

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
 * Fragment que muestra la gráfica de sueño.
 */
class GraficaSleepView : Fragment() {

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
        return inflater.inflate(R.layout.fragment_grafica_sleep_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartSleep = view.findViewById<LineChart>(R.id.graficoLineas_Suenno)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de sueño de la base de datos

        val datosSueno = MainActivity.databaseHelper?.obtenerTodosLosSuenos(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de sueño
        val entriesSleep = datosSueno?.mapIndexed { index, sueno ->
            Entry(index.toFloat(), sueno.horasSueno.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetSleep = LineDataSet(entriesSleep, getString(R.string.hrs))
        dataSetSleep.color = Color.BLUE
        dataSetSleep.valueTextColor = Color.BLACK
        dataSetSleep.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataSleep = LineData(dataSetSleep)
        chartSleep.data = dataSleep
        chartSleep.setTouchEnabled(true)
        chartSleep.setPinchZoom(true)
        chartSleep.description.text = getString(R.string.horas_de_sueno)
        chartSleep.setNoDataText(getString(R.string.no_hay_datos_disponibles))
        chartSleep.invalidate()
    }

}