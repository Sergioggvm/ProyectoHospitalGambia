package com.example.proyectohospitalgambia.feature.vistaIntroducirGlycemia

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.GlucosaSangre
import com.example.proyectohospitalgambia.core.domain.model.datosPols.PresionSanguinea
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.feature.vistaIntroducirBloodPressure.IntroducirBloodPressureViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class IntroducirGlycemiaView : Fragment() {

    private lateinit var btnGuardar: FloatingActionButton

    // Declaración de variables para los elementos del formulario
    private lateinit var edtGlucosa: EditText

    private val viewModel: IntroducirGlycemiaViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_introducir_glycemia, container, false)

        btnGuardar = view.findViewById(R.id.btn_guardarGlycemia)

        // Inicialización de elementos del formulario
        edtGlucosa = view.findViewById(R.id.et_glycemia)

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnGuardar.setOnClickListener {

            val usuarioActivo = MainActivity.usuario

            // Obtener los datos del formulario
            val datosFormulario = obtenerDatosFormulario()
            // Verificar si se obtuvieron los datos del formulario correctamente
            if (datosFormulario != null) {

                // Mostrar un mensaje de éxito
                Toast.makeText(context, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()

                // Generar IDs aleatorios como strings
                val idPols = generarIdAleatorio()
                val idBook = MainActivity.usuario?.id.toString() // Asumiendo que MainActivity.idUsuario es un Long o un Int

                val pol = Pol(idPols, idBook, datosFormulario.toString(), "false")

                if (usuarioActivo != null) {
                    usuarioActivo.pols.add(pol)
                }

                // Llamar al método del ViewModel para insertar datos
                var resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                if (resultado){

                    Toast.makeText(requireContext(), "Nuevo registro correcto", Toast.LENGTH_SHORT).show()

                    // Navegar hacia atrás
                    requireActivity().supportFragmentManager.popBackStack()

                } else {
                    Toast.makeText(requireContext(), "Error al completar el nuevo registro", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    // Método para obtener los datos del formulario y crear el JSON
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText
        val glucosaText = edtGlucosa.text.toString()

        // Verificar si algún campo está vacío
        if (glucosaText.isEmpty()) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Convertir los valores a tipos numéricos
        val glucosa = glucosaText.toInt()

        // Obtener la fecha y hora actual
        val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
            Date()
        )


        val glucosaSangre = GlucosaSangre(
            fechaRealizacion = currentDateAndTime,
            glucosa = glucosa
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", glucosaSangre.tipoPol)
        jsonObject.put("FechaInsercion", glucosaSangre.fechaRealizacion)
        jsonObject.put("Glucosa", glucosaSangre.glucosa)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtGlucosa.text.clear()

        return jsonObject
    }
}