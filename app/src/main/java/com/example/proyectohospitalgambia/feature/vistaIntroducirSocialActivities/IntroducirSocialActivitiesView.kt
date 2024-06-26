package com.example.proyectohospitalgambia.feature.vistaIntroducirSocialActivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ActividadesSociales
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Clase IntroducirSocialActivitiesView.
 *
 * Esta clase representa la vista para introducir las actividades sociales en la aplicación.
 *
 * @property edtMinutosActividadesSociales EditText para introducir los minutos de las actividades sociales.
 * @property edtNotasActividadesSociales EditText para introducir las notas de las actividades sociales.
 * @property viewModel ViewModel para la vista de introducir las actividades sociales.
 *
 * @method onCreateView Método que se llama para crear la vista del fragmento.
 * @method onViewCreated Método que se llama inmediatamente después de que la vista del fragmento se ha creado.
 * @method generarIdAleatorio Método para generar un ID aleatorio.
 * @method obtenerDatosFormulario Método para obtener los datos del formulario y crear el JSON.
 */
class IntroducirSocialActivitiesView : Fragment() {

    private lateinit var edtMinutosActividadesSociales: EditText
    private lateinit var edtNotasActividadesSociales: EditText

    private val viewModel: IntroducirSocialActivitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view =
            inflater.inflate(R.layout.fragment_introducir_social_activities, container, false)

        edtMinutosActividadesSociales = view.findViewById(R.id.et_minutesSocialActivities)
        edtNotasActividadesSociales = view.findViewById(R.id.et_notesSocialActivities)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDone: FloatingActionButton = view.findViewById(R.id.btn_guardarSocialActivities)

        btnDone.setOnClickListener {

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

    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText
        val minutosActividadesSociales = edtMinutosActividadesSociales.text.toString().toIntOrNull()
        val notas = edtNotasActividadesSociales.text.toString()

        // Verificar si algún campo está vacío
        if (minutosActividadesSociales == null || notas.isEmpty()) {
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()
            )

        val actividadesSociales = ActividadesSociales(
            fechaRealizacion = currentDateAndTime,
            minutosActividad = minutosActividadesSociales,
            notas = notas
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", actividadesSociales.tipoPol)
        jsonObject.put("FechaInsercion", actividadesSociales.fechaRealizacion)
        jsonObject.put("MinutosActividadesSociales", actividadesSociales.minutosActividad)
        jsonObject.put("Notas", actividadesSociales.notas)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtMinutosActividadesSociales.text.clear()
        edtNotasActividadesSociales.text.clear()

        return jsonObject
    }
}