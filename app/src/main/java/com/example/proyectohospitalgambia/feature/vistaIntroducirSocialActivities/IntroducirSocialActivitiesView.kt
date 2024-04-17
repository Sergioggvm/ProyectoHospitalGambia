package com.example.proyectohospitalgambia.feature.vistaIntroducirSocialActivities

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ActividadesSociales
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IntroducirSocialActivitiesView : Fragment() {

    private lateinit var edtMinutosActividadesSociales: EditText
    private lateinit var edtNotasActividadesSociales: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_introducir_social_activities, container, false)

        edtMinutosActividadesSociales = view.findViewById(R.id.et_minutesSocialActivities)
        edtNotasActividadesSociales = view.findViewById(R.id.et_notesSocialActivities)

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
        // Obtener los valores de los EditText
        val minutosActividadesSociales = edtMinutosActividadesSociales.text.toString().toIntOrNull()
        val notas = edtNotasActividadesSociales.text.toString()

        // Verificar si algún campo está vacío
        if (minutosActividadesSociales == null || notas.isEmpty()) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
            Date()
        )

        val actividadesSociales = ActividadesSociales(
            fechaRealizacion = currentDateAndTime,
            minutosActividad = minutosActividadesSociales,
            notas = notas
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("Tipo", actividadesSociales.tipoPol)
        jsonObject.put("FechaRealizacion", actividadesSociales.fechaRealizacion)
        jsonObject.put("MinutosActividadesSociales", actividadesSociales.minutosActividad)
        jsonObject.put("Notas", actividadesSociales.notas)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtMinutosActividadesSociales.text.clear()
        edtNotasActividadesSociales.text.clear()

        return jsonObject
    }
}