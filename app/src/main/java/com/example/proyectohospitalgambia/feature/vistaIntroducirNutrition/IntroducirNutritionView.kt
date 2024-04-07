package com.example.proyectohospitalgambia.feature.vistaIntroducirNutrition

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.feature.vistaGraficaNutrition.GraficaNutritionView

class IntroducirNutritionView : Fragment() {

    companion object {
        fun newInstance() = IntroducirNutritionView()
    }

    private val viewModel: IntroducirNutritionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_introducir_nutrition, container, false)
    }

    class IntroducirNutritionView : Fragment() {
        // ...
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val btnGuardar = view.findViewById<Button>(R.id.btn_guardarNutrition)
            btnGuardar.setOnClickListener {
                val morning = view.findViewById<EditText>(R.id.et_morningNutrition).text.toString().toInt()
                val afternoon = view.findViewById<EditText>(R.id.et_afternoonNutrition).text.toString().toInt()
                val evening = view.findViewById<EditText>(R.id.et_eveningNutrition).text.toString().toInt()
                val total = view.findViewById<EditText>(R.id.et_totalNutrition).text.toString().toInt()

                val bundle = Bundle()
                bundle.putInt("morning", morning)
                bundle.putInt("afternoon", afternoon)
                bundle.putInt("evening", evening)
                bundle.putInt("total", total)

                val graficaNutritionView = GraficaNutritionView()
                graficaNutritionView.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerViewHost, graficaNutritionView)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}