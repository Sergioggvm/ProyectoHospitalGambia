package com.example.proyectohospitalgambia.feature.vistaGraficoOsat

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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GraficoOsatView.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraficoOsatView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico_osat_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartOsat = view.findViewById<LineChart>(R.id.graficoLineas_SaturacionOxigeno)

        // Datos de prueba para la saturación de oxígeno
        val entriesOsat = ArrayList<Entry>()
        entriesOsat.add(Entry(0f, 98f)) // Día 1
        entriesOsat.add(Entry(1f, 97f)) // Día 2
        entriesOsat.add(Entry(2f, 99f)) // Día 3
        entriesOsat.add(Entry(3f, 98f)) // Día 4
        entriesOsat.add(Entry(4f, 97f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetOsat = LineDataSet(entriesOsat, "pct")
        dataSetOsat.color = Color.RED

        // Agregar el conjunto de datos a los datos de la línea
        val lineDataOsat = LineData(dataSetOsat)

        // Aplicar los datos al gráfico y refrescarlo
        chartOsat.data = lineDataOsat
        chartOsat.invalidate() // Refresca el gráfico
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraficoOsatView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraficoOsatView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}