package com.example.proyectohospitalgambia.feature.vistaGraficaPhysicalActivity

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
 * Fragment que muestra la gráfica de actividad física.
 */
class GraficaPhysicalActivityView : Fragment() {

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
        return inflater.inflate(R.layout.fragment_grafica_physical_activity_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartActividadFisica = view.findViewById<LineChart>(R.id.graficoLineas_ActividadFisica)
        val chartPasos = view.findViewById<LineChart>(R.id.graficoLineas_Pasos)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de actividad física de la base de datos
        val datosActividadFisica =
            MainActivity.databaseHelper?.obtenerTodosLosDatosActividadFisica(idUsuarioActual!!)
        // Crear las entradas de la gráfica a partir de los datos de actividad física
        val entriesAerobica = datosActividadFisica?.mapIndexed { index, actividadFisica ->
            Entry(index.toFloat(), actividadFisica.aerobico.toFloat())
        }
        val entriesAnaerobica = datosActividadFisica?.mapIndexed { index, actividadFisica ->
            Entry(index.toFloat(), actividadFisica.anaerobico.toFloat())
        }

        // Crear los conjuntos de datos y personalizarlos
        val dataSetAerobica = LineDataSet(entriesAerobica, getString(R.string.aerobico))
        dataSetAerobica.color = Color.RED

        val dataSetAnaerobica = LineDataSet(entriesAnaerobica, getString(R.string.anaerobico))
        dataSetAnaerobica.color = Color.BLUE

        // Agregar los conjuntos de datos a los datos de la línea
        val lineDataActividadFisica = LineData(dataSetAerobica, dataSetAnaerobica)

        // Aplicar los datos al gráfico y refrescarlo
        chartActividadFisica.data = lineDataActividadFisica
        chartActividadFisica.invalidate() // Refresca el gráfico

        // Crear las entradas de la gráfica de pasos a partir de los datos de actividad física
        val entriesPasos = datosActividadFisica?.mapIndexed { index, actividadFisica ->
            Entry(index.toFloat(), actividadFisica.pasos.toFloat())
        }

        // Crear el conjunto de datos de pasos y personalizarlo
        val dataSetPasos = LineDataSet(entriesPasos, getString(R.string.pasos))
        dataSetPasos.color = Color.GREEN

        // Crear los datos de la línea de pasos
        val lineDataPasos = LineData(dataSetPasos)

        // Aplicar los datos al gráfico de pasos y refrescarlo
        chartPasos.data = lineDataPasos
        chartPasos.invalidate() // Refresca el gráfico
    }

}