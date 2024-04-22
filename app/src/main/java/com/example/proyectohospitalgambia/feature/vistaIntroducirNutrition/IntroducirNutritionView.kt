package com.example.proyectohospitalgambia.feature.vistaIntroducirNutrition

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ValorEnergetico
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.feature.vistaGraficaNutrition.GraficaNutritionView
import com.example.proyectohospitalgambia.feature.vistaIntroducirMoodAndEnergy.IntroducirMoodAndEnergyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class IntroducirNutritionView : Fragment() {


    private lateinit var edtKcalManana: EditText
    private lateinit var edtKcalTarde: EditText
    private lateinit var edtKcalNoche: EditText
    private lateinit var edtKcalTotal: EditText
    private lateinit var edtNotas: EditText

    private val viewModel: IntroducirNutritionViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_introducir_nutrition, container, false)

        edtKcalManana = view.findViewById(R.id.et_morningNutrition)
        edtKcalTarde = view.findViewById(R.id.et_afternoonNutrition)
        edtKcalNoche = view.findViewById(R.id.et_eveningNutrition)
        edtKcalTotal = view.findViewById(R.id.et_totalNutrition)
        edtNotas = view.findViewById(R.id.et_notesNutrition)

        return view
    }
        // ...
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

        private fun obtenerDatosFormulario(): JSONObject? {
            // Obtener los valores de los EditText
            val kcalManana = edtKcalManana.text.toString().toIntOrNull()
            val kcalTarde = edtKcalTarde.text.toString().toIntOrNull()
            val kcalNoche = edtKcalNoche.text.toString().toIntOrNull()
            val kcalTotal = edtKcalTotal.text.toString().toIntOrNull()
            val notas = edtNotas.text.toString()

            // Verificar si algún campo está vacío
            if (kcalManana == null || kcalTarde == null || kcalNoche == null || kcalTotal == null || notas.isEmpty()) {
                // Mostrar un Toast indicando que algún campo está vacío
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return null // Devolver null para indicar que no se han completado todos los campos
            }

            // Obtener la fecha y hora actual
            val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
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