package com.example.proyectohospitalgambia.feature.vistaGraficaMoodAndEnergy

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
 * Use the [GraficaMoodAndEnergyView.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraficaMoodAndEnergyView : Fragment() {
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
        return inflater.inflate(R.layout.fragment_grafica_mood_and_energy_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartMood = view.findViewById<LineChart>(R.id.graficoLineas_EstadoAnimo)
        val chartEnergy = view.findViewById<LineChart>(R.id.graficoLineas_Energia)

        // Agrega datos a chartMood
        val entriesMood = ArrayList<Entry>()
        entriesMood.add(Entry(0f, 5f)) // Día 1
        entriesMood.add(Entry(1f, 3f)) // Día 2
        entriesMood.add(Entry(2f, 1f)) // Día 2
        entriesMood.add(Entry(3f, 7f)) // Día 2
        entriesMood.add(Entry(4f, 10f)) // Día 2
        // ... Agrega más datos según sea necesario

        val dataSetMood = LineDataSet(entriesMood, "Mood")
        dataSetMood.color = Color.RED

        val lineDataMood = LineData(dataSetMood)
        chartMood.data = lineDataMood
        chartMood.invalidate() // Refresca el gráfico

        // Agrega datos a chartEnergy
        val entriesEnergy = ArrayList<Entry>()
        entriesEnergy.add(Entry(0f, 7f)) // Día 1
        entriesEnergy.add(Entry(1f, 1f)) // Día 2
        entriesEnergy.add(Entry(2f, 3f)) // Día 2
        entriesEnergy.add(Entry(3f, 4f)) // Día 2
        entriesEnergy.add(Entry(4f, 5f)) // Día 2
        // ... Agrega más datos según sea necesario

        val dataSetEnergy = LineDataSet(entriesEnergy, "Energy")
        dataSetEnergy.color = Color.BLUE

        val lineDataEnergy = LineData(dataSetEnergy)
        chartEnergy.data = lineDataEnergy
        chartEnergy.invalidate() // Refresca el gráfico
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraficoMoodAndEnergyView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraficaMoodAndEnergyView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}