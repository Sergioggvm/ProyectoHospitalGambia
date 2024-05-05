package com.example.proyectohospitalgambia.feature.vistaGraficaGlycemia

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
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Fragment que muestra las gráficas de glucemia.
 */
class GraficaGlycemiaView : Fragment() {

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
        return inflater.inflate(R.layout.fragment_grafica_glycemia_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartGlucemia = view.findViewById<LineChart>(R.id.graficoLineas_Glucosa)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de glucemia de la base de datos
        val datosGlucemia =
            MainActivity.databaseHelper?.obtenerTodosLosDatosGlucemia(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de glucemia
        val entriesGlucemia = datosGlucemia?.mapIndexed { index, glucemia ->
            Entry(index.toFloat(), glucemia.glucosa.toFloat())
        }

        // Crear un mapeo de índices a fechas
        val indexToDateMap =
            datosGlucemia?.associate { it.glucosa.toFloat() to it.fechaRealizacion }

        // Crear el conjunto de datos y personalizarlo
        val dataSetGlucemia = LineDataSet(entriesGlucemia, getString(R.string.glucemia))
        dataSetGlucemia.color = Color.BLUE
        dataSetGlucemia.valueTextColor = Color.BLACK
        dataSetGlucemia.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataGlucemia = LineData(dataSetGlucemia)
        chartGlucemia.data = dataGlucemia
        chartGlucemia.setTouchEnabled(true)
        chartGlucemia.setPinchZoom(true)
        chartGlucemia.description.text = getString(R.string.glucemia)
        chartGlucemia.setNoDataText(getString(R.string.no_hay_datos_disponibles))

        // Configurar el formateador del eje X para mostrar las fechas
        chartGlucemia.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return indexToDateMap?.get(value)
                    ?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it) }
                    ?: value.toString()
            }
        }

        chartGlucemia.invalidate()
    }

}