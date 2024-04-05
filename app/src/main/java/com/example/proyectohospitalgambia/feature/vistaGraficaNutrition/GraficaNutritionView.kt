package com.example.proyectohospitalgambia.feature.vistaGraficaNutrition

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.echo.holographlibrary.Bar
import com.echo.holographlibrary.BarGraph
import com.example.proyectohospitalgambia.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GraficaNutritionView.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraficaNutritionView : Fragment() {
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
        return inflater.inflate(R.layout.fragment_grafica_nutrition_view, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val graph = view.findViewById<BarGraph>(R.id.graficoBarras_Nutrition)

        arguments?.let {
            val morning = it.getInt("morning")
            val afternoon = it.getInt("afternoon")
            val evening = it.getInt("evening")
            val total = it.getInt("total")

            val points = arrayListOf(
                Bar().apply {
                    color = Color.RED
                    name = "Mañana"
                    value = morning.toFloat()
                },
                Bar().apply {
                    color = Color.MAGENTA
                    name = "Tarde"
                    value = afternoon.toFloat()
                },
                Bar().apply {
                    color = Color.CYAN
                    name = "Noche"
                    value = evening.toFloat()
                },
                Bar().apply {
                    color = Color.YELLOW
                    name = "Total"
                    value = total.toFloat()
                }
            )
            graph.bars = points
        }

    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraficaNutritionView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraficaNutritionView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}