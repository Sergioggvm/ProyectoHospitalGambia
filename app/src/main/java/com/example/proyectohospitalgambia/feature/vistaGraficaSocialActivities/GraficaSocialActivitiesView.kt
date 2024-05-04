package com.example.proyectohospitalgambia.feature.vistaGraficaSocialActivities

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
 * Fragment que muestra la gráfica de actividades sociales.
 */
class GraficaSocialActivitiesView : Fragment() {

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
        return inflater.inflate(R.layout.fragment_grafica_social_activities_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartSocialActivities =
            view.findViewById<LineChart>(R.id.graficoLineas_ActividadesSociales)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de las actividades sociales de la base de datos
        val datosActividadesSociales =
            MainActivity.databaseHelper?.obtenerTodosLosDatosActividadesSociales(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de las actividades sociales
        val entriesSocialActivities =
            datosActividadesSociales?.mapIndexed { index, actividadesSociales ->
                Entry(index.toFloat(), actividadesSociales.minutosActividad.toFloat())
            }

        // Crear el conjunto de datos y personalizarlo
        val dataSetSocialActivities = LineDataSet(
            entriesSocialActivities, getString(R.string.minutos)
        )
        dataSetSocialActivities.color = Color.BLUE
        dataSetSocialActivities.valueTextColor = Color.BLACK

        // Crear los datos de la línea
        val lineDataSocialActivities = LineData(dataSetSocialActivities)

        // Aplicar los datos al gráfico y refrescarlo
        chartSocialActivities.data = lineDataSocialActivities
        chartSocialActivities.invalidate() // Refresca el gráfico
    }

}