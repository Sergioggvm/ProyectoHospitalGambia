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
import com.example.proyectohospitalgambia.feature.vistaInicio.InicioView
import org.json.JSONObject
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
            // Obtener los datos del formulario
            val datosFormulario = obtenerDatosFormulario()

            // Generar IDs aleatorios como strings
            val idPols = generarIdAleatorio()
            val idBook = MainActivity.idUsuario.toString() // Asumiendo que MainActivity.idUsuario es un Long o un Int

            // Llamar al método del ViewModel para insertar datos
            var resultado = viewModel.insertarDatosEnBaseDeDatos(idPols, idBook, datosFormulario.toString())

            if (resultado){

                Toast.makeText(requireContext(), "Nuevo registro correcto", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Error al completar el nuevo registro", Toast.LENGTH_SHORT).show()
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
    private fun obtenerDatosFormulario(): JSONObject {
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

        val jsonObject = JSONObject()
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

        return jsonObject
    }


}