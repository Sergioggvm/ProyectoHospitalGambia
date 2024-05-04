package com.example.proyectohospitalgambia.feature.vistaIntroducirNutrition

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
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ValorEnergetico
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
/**
 * Fragment que permite al usuario introducir datos de nutrición.
 */
class IntroducirNutritionView : Fragment() {


    private lateinit var edtKcalManana: EditText
    private lateinit var edtKcalTarde: EditText
    private lateinit var edtKcalNoche: EditText
    private lateinit var edtKcalTotal: EditText
    private lateinit var edtNotas: EditText

    private val viewModel: IntroducirNutritionViewModel by viewModels()


    /**
     * Método que se llama para tener la vista del fragment inflada y lista.
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar cualquier vista en el fragment.
     * @param container Si no es nulo, esta es la vista principal a la que se debe adjuntar la UI del fragment.
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     * @return Retorna la vista del fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_introducir_nutrition, container, false)

        edtKcalManana = view.findViewById(R.id.et_morningNutrition)
        edtKcalTarde = view.findViewById(R.id.et_afternoonNutrition)
        edtKcalNoche = view.findViewById(R.id.et_eveningNutrition)
        edtKcalTotal = view.findViewById(R.id.et_totalNutrition)
        edtNotas = view.findViewById(R.id.et_notesNutrition)

        return view
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDone: FloatingActionButton = view.findViewById(R.id.btn_guardarNutrition)

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

    /**
     * Método para generar un ID aleatorio.
     *
     * @return Retorna un string que representa un UUID.
     */
    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Método para obtener los datos del formulario y crear el JSON.
     *
     * @return Retorna un JSONObject que contiene los datos del formulario, o null si algún campo está vacío.
     */
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText
        val kcalManana = edtKcalManana.text.toString().toIntOrNull()
        val kcalTarde = edtKcalTarde.text.toString().toIntOrNull()
        val kcalNoche = edtKcalNoche.text.toString().toIntOrNull()
        val kcalTotal = edtKcalTotal.text.toString().toIntOrNull()
        val notas = edtNotas.text.toString()

        // Verificar si algún campo está vacío
        if (kcalManana == null || kcalTarde == null || kcalNoche == null || kcalTotal == null || notas.isEmpty()) {
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()
            )

        val valorEnergetico = ValorEnergetico(
            fechaRealizacion = currentDateAndTime,
            kcalManana = kcalManana,
            kcalTarde = kcalTarde,
            kcalNoche = kcalNoche,
            kcalTotal = kcalTotal,
            notas = notas
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", valorEnergetico.tipoPol)
        jsonObject.put("FechaInsercion", valorEnergetico.fechaRealizacion)
        jsonObject.put("KcalManana", valorEnergetico.kcalManana)
        jsonObject.put("KcalTarde", valorEnergetico.kcalTarde)
        jsonObject.put("KcalNoche", valorEnergetico.kcalNoche)
        jsonObject.put("KcalTotal", valorEnergetico.kcalTotal)
        jsonObject.put("Notas", valorEnergetico.notas)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtKcalManana.text.clear()
        edtKcalTarde.text.clear()
        edtKcalNoche.text.clear()
        edtKcalTotal.text.clear()
        edtNotas.text.clear()

        return jsonObject
    }
}