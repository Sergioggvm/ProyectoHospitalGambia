package com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.people.PeopleUser
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.feature.vistaInicio.InicioView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoRegistroServidorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoRegistroServidorView : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var btnGuardar: Button

    private lateinit var btnListar: Button

    // Declaración de variables para los elementos del formulario
    private lateinit var edtTextoDia: EditText
    private lateinit var edtTextoMes: EditText
    private lateinit var edtTextoAnio: EditText
    private lateinit var edtTextoHora: EditText
    private lateinit var edtTextoMinutos: EditText
    private lateinit var edtTextoResumen: EditText
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var edtTextoDetalles: EditText
    private lateinit var spinner3: Spinner
    private lateinit var cbPaginaPrivada: CheckBox

    private val viewModel: NuevoRegistroServidorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nuevo_registro_servidor_view, container, false)

        btnGuardar = view.findViewById(R.id.btn_guardar)

        btnListar = view.findViewById(R.id.button2)

        // Inicialización de elementos del formulario
        edtTextoDia = view.findViewById(R.id.edt_textoDia)
        edtTextoMes = view.findViewById(R.id.edt_textoMes)
        edtTextoAnio = view.findViewById(R.id.edt_textoAnio)
        edtTextoHora = view.findViewById(R.id.edt_textoHora)
        edtTextoMinutos = view.findViewById(R.id.edt_textoMinutos)
        edtTextoResumen = view.findViewById(R.id.edt_textoResumen)
        spinner1 = view.findViewById(R.id.spinner1)
        spinner2 = view.findViewById(R.id.spinner2)
        edtTextoDetalles = view.findViewById(R.id.edt_textoDetalles)
        spinner3 = view.findViewById(R.id.spinner3)
        cbPaginaPrivada = view.findViewById(R.id.cb_paginaPrivada)

        // Configura el adaptador para el primer Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opcion_medicina,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
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

        spinner1.onItemSelectedListener = this
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

                val pol = Pol(idPols, idBook, datosFormulario.toString())

                if (usuarioActivo != null) {
                    usuarioActivo.pols.add(pol)
                }

                // Llamar al método del ViewModel para insertar datos
                var resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                if (resultado){

                    Toast.makeText(requireContext(), "Nuevo registro correcto", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Error al completar el nuevo registro", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }


        }

        btnListar.setOnClickListener {

            viewModel.listarDatos()
        }

    }

    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    // Método para obtener los datos del formulario y crear el JSON
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText y Spinner
        val dia = edtTextoDia.text.toString()
        val mes = edtTextoMes.text.toString()
        val anio = edtTextoAnio.text.toString()
        val hora = edtTextoHora.text.toString()
        val minutos = edtTextoMinutos.text.toString()
        val resumen = edtTextoResumen.text.toString()
        val dominioYContexto = spinner1.selectedItem.toString()
        val relevancia = spinner2.selectedItem.toString()
        val detalles = edtTextoDetalles.text.toString()
        val relevanciaDetalles = spinner3.selectedItem.toString()
        val paginaPrivada = cbPaginaPrivada.isChecked

        // Verificar si algún campo está vacío
        if (dia.isEmpty() || mes.isEmpty() || anio.isEmpty() || hora.isEmpty() || minutos.isEmpty() ||
            resumen.isEmpty() || dominioYContexto.isEmpty() || relevancia.isEmpty() || detalles.isEmpty() ||
            relevanciaDetalles.isEmpty()
        ) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("Tipo pol", "LibroVida")
        jsonObject.put("FechaInsercion", currentDateAndTime)
        jsonObject.put("dia", dia)
        jsonObject.put("mes", mes)
        jsonObject.put("anio", anio)
        jsonObject.put("hora", hora)
        jsonObject.put("minutos", minutos)
        jsonObject.put("resumen", resumen)
        jsonObject.put("dominioYContexto", dominioYContexto)
        jsonObject.put("relevancia", relevancia)
        jsonObject.put("detalles", detalles)
        jsonObject.put("relevanciaDetalles", relevanciaDetalles)
        jsonObject.put("paginaPrivada", paginaPrivada)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtTextoDia.text.clear()
        edtTextoMes.text.clear()
        edtTextoAnio.text.clear()
        edtTextoHora.text.clear()
        edtTextoMinutos.text.clear()
        edtTextoResumen.text.clear()
        spinner1.setSelection(0)
        spinner2.setSelection(0)
        edtTextoDetalles.text.clear()
        spinner3.setSelection(0)
        cbPaginaPrivada.isChecked = false

        return jsonObject
    }

}