package com.example.proyectohospitalgambia.feature.vistaIntroducirWeight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Peso
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Clase IntroducirWeightView.
 *
 * Esta clase representa la vista para introducir el peso en la aplicación.
 *
 * @property btnGuardar Botón para guardar los datos ingresados.
 * @property edtPeso EditText para introducir el peso.
 * @property viewModel ViewModel para la vista de introducir el peso.
 *
 * @method onCreateView Método que se llama para crear la vista del fragmento.
 * @method onViewCreated Método que se llama inmediatamente después de que la vista del fragmento se ha creado.
 * @method generarIdAleatorio Método para generar un ID aleatorio.
 * @method obtenerDatosFormulario Método para obtener los datos del formulario y crear el JSON.
 */
class IntroducirWeightView : Fragment() {

    private lateinit var btnGuardar: FloatingActionButton

    // Declaración de variables para los elementos del formulario
    private lateinit var edtPeso: EditText

    private val viewModel: IntroducirWeightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_introducir_weight, container, false)

        btnGuardar = view.findViewById(R.id.btn_guardarWeight)

        // Inicialización de elementos del formulario
        edtPeso = view.findViewById(R.id.et_weight)

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
                Toast.makeText(context, R.string.toast_datos_guardados, Toast.LENGTH_SHORT).show()

                // Generar IDs aleatorios como strings
                val idPols = generarIdAleatorio()
                val idBook =
                    MainActivity.usuario?.id.toString() // Asumiendo que MainActivity.idUsuario es un Long o un Int

                val pol = Pol(idPols, idBook, datosFormulario.toString(), "false")

                usuarioActivo?.pols?.add(pol)

                // Llamar al método del ViewModel para insertar datos
                val resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                if (resultado) {
                    // Navegar hacia atrás
                    requireActivity().supportFragmentManager.popBackStack()

                } else {
                    Toast.makeText(
                        requireContext(), R.string.toast_datos_no_guardados, Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(context, R.string.toast_complete_campos, Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    // Método para obtener los datos del formulario y crear el JSON
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText
        val pesoText = edtPeso.text.toString()

        // Verificar si algún campo está vacío
        if (pesoText.isEmpty()) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, R.string.toast_complete_campos, Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Convertir los valores a tipos numéricos
        val pesoKg = pesoText.toInt()

        // Obtener la fecha y hora actual
        val currentDateAndTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()
            )


        val peso = Peso(
            fechaRealizacion = currentDateAndTime, kg = pesoKg
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", peso.tipoPol)
        jsonObject.put("FechaInsercion", peso.fechaRealizacion)
        jsonObject.put("kg", peso.kg)

        Log.d("JSON Data", jsonObject.toString())


        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtPeso.text.clear()

        return jsonObject
    }
}