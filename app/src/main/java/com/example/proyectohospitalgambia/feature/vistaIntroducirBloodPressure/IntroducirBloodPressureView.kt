package com.example.proyectohospitalgambia.feature.vistaIntroducirBloodPressure

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.LibroVida
import com.example.proyectohospitalgambia.core.domain.model.datosPols.PresionSanguinea
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor.NuevoRegistroServidorViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class IntroducirBloodPressureView : Fragment() {

    private lateinit var btnGuardar: Button

    // Declaración de variables para los elementos del formulario
    private lateinit var edtSistolico: EditText
    private lateinit var edtDiastolico: EditText
    private lateinit var edtFrecuenciaCardiaca: EditText


    private val viewModel: IntroducirBloodPressureViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_introducir_blood_pressure, container, false)

        btnGuardar = view.findViewById(R.id.btn_guardarBloodPressure)

        // Inicialización de elementos del formulario
        edtSistolico = view.findViewById(R.id.et_systolic)
        edtDiastolico = view.findViewById(R.id.et_diastolic)
        edtFrecuenciaCardiaca = view.findViewById(R.id.et_heart_rate)

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
        val sistolicoText = edtSistolico.text.toString()
        val diastolicoText = edtDiastolico.text.toString()
        val frecuenciaCardiacaText = edtFrecuenciaCardiaca.text.toString()

        // Verificar si algún campo está vacío
        if (sistolicoText.isEmpty() || diastolicoText.isEmpty() || frecuenciaCardiacaText.isEmpty()) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Convertir los valores a tipos numéricos
        val sistolico = sistolicoText.toInt()
        val diastolico = diastolicoText.toInt()
        val frecuenciaCardiaca = frecuenciaCardiacaText.toInt()

        // Obtener la fecha y hora actual
        val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
            Date()
        )


        val presionSanguinea = PresionSanguinea(
            fechaRealizacion = currentDateAndTime,
            sistolico = sistolico,
            diastolico = diastolico,
            frecuenciaCardiaca = frecuenciaCardiaca
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", presionSanguinea.tipoPol)
        jsonObject.put("FechaInsercion", presionSanguinea.fechaRealizacion)
        jsonObject.put("Sistolico", presionSanguinea.sistolico)
        jsonObject.put("Diastolico", presionSanguinea.diastolico)
        jsonObject.put("FrecuenciaCardiaca", presionSanguinea.frecuenciaCardiaca)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtSistolico.text.clear()
        edtDiastolico.text.clear()
        edtFrecuenciaCardiaca.text.clear()

        return jsonObject
    }
}