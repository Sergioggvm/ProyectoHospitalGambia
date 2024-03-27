package com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.proyectohospitalgambia.R

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoRegistroServidorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoRegistroServidorView : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nuevo_registro_servidor_view, container, false)

        spinner = view.findViewById(R.id.spinner1)
        val spinner2 = view.findViewById<Spinner>(R.id.spinner2)
        val spinner3 = view.findViewById<Spinner>(R.id.spinner3)

        // Configura el adaptador para el primer Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opcion_medicina,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Configura el adaptador para el segundo Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.condiciones_salud,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }

        // Configura el adaptador para el tercer Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tipo_relevancia,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner3.adapter = adapter
        }

        spinner.onItemSelectedListener = this
        spinner2.onItemSelectedListener = this
        spinner3.onItemSelectedListener = this

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Acciones cuando se selecciona un ítem del spinner
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Acciones cuando no se selecciona ningún ítem del spinner
    }
}