package com.example.proyectohospitalgambia.feature.vistaIntroducirSleep

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Sueno
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IntroducirSleepView : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var edtHorasSueno: EditText
    private lateinit var edtNotas: EditText
    private lateinit var spinnerCalidadSueno: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            val view = inflater.inflate(R.layout.fragment_introducir_sleep, container, false)

            edtHorasSueno = view.findViewById(R.id.et_horasSleep)
            edtNotas = view.findViewById(R.id.et_notesSleep)
            spinnerCalidadSueno = view.findViewById(R.id.spinner_sleepQuality)




        spinnerCalidadSueno = view.findViewById(R.id.spinner_sleepQuality)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.calidad_suenno, // Define este array en tu archivo strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCalidadSueno.adapter = adapter
        }

        spinnerCalidadSueno.onItemSelectedListener = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDone: Button = view.findViewById(R.id.btn_guardarSocialActivities)

        btnDone.setOnClickListener {
            val datosFormulario = obtenerDatosFormulario()
            if (datosFormulario != null) {
                Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText y Spinner
        val horasSueno = edtHorasSueno.text.toString().toIntOrNull()
        val calidadSueno = spinnerCalidadSueno.selectedItem.toString()
        val notas = edtNotas.text.toString()

        // Verificar si algún campo está vacío
        if (horasSueno == null || calidadSueno.isEmpty() || notas.isEmpty()) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
            Date()
        )

        val sueno = Sueno(
            fechaRealizacion = currentDateAndTime,
            horasSueno = horasSueno,
            calidadSueno = calidadSueno,
            notas = notas
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("Tipo",  sueno.tipoPol)
        jsonObject.put("FechaRealizacion", sueno.fechaRealizacion)
        jsonObject.put("HorasSueno", sueno.horasSueno)
        jsonObject.put("CalidadSueno", sueno.calidadSueno)
        jsonObject.put("Notas", sueno.notas)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtHorasSueno.text.clear()
        edtNotas.text.clear()
        spinnerCalidadSueno.setSelection(0)

        return jsonObject
    }




    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}