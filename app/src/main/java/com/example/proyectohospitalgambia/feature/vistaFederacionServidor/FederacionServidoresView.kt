package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.network.federeacionServidorApi
import org.json.JSONObject

import com.example.proyectohospitalgambia.app.retrofit.RetrofitClient
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.feature.vistaIntroducirWeight.IntroducirWeightViewModel
import com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor.NuevoRegistroServidorViewModel
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import okhttp3.*
import okhttp3.Callback



class FederacionServidoresView : Fragment() {

    private lateinit var btnNuevoRegistro: ImageButton
    private lateinit var btnRecargaServidor: ImageButton

    private val viewModel: FederacionServidoresViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val federacionServidoresView = inflater.inflate(R.layout.fragment_federacion_servidores_view, container, false)

        btnNuevoRegistro = federacionServidoresView.findViewById(R.id.btn_nuevoRegistro)
        btnRecargaServidor = federacionServidoresView.findViewById(R.id.btn_recargaServidor)

        // Agrega OnClickListener al botón btnJugarLocal
        btnNuevoRegistro.setOnClickListener {
            // Navega al fragmento de VistaTableroView cuando se hace clic en el botón
            findNavController().navigate(R.id.action_federacionServidoresView_to_nuevoRegistroServidorView)
        }

        val usuarioEncontrado = MainActivity.usuario

        // Crear una instancia del servicio de la API
        val apiService = RetrofitClient.instance.create(federeacionServidorApi::class.java)

        // Configurar el OnClickListener para el botón btnRecargaServidor
        btnRecargaServidor.setOnClickListener {
            // Recuperar las pols
            val pols = viewModel.recuperarDatos()

            // Crear el encabezado del JSON
            val jsonString2 = """
                {
                  "creation_info": {
                    "node": "mygnuhealth",
                    "timestamp": "2024-03-26T18:13:46.604899",
                    "user": "ESPGNU777ORG"
                  },
                  "domain": "medical",
                  "fsynced": true,
                  "genetic_info": null,
                  "id": "2bb7e529-c583-4d6e-ad24-e858196f98af",
                  "measurements": [
                    {
                      "hr": 124444445
                    }
                  ]
                }
                """.trimIndent()

            // Variable para controlar si se ha agregado al menos una pol
            var alMenosUnaPolAgregada = false

            var jsonConcatenar = ""

            // Verificar cada pol
            for (pol in pols) {
                if (usuarioEncontrado != null && usuarioEncontrado.id == pol.book && pol.isSubido.equals("false", ignoreCase = true)) {

                    jsonConcatenar = "${pol.data.trimEnd('}')},${jsonString2.trimStart('{')}"
                        .trimIndent() // Eliminar espacios en blanco adicionales

                    alMenosUnaPolAgregada = true
                }
            }

            // Insertar datos en el servidor solo si al menos una pol cumple con los requisitos
            if (alMenosUnaPolAgregada) {
                val jsonMediaType = "application/json; charset=utf-8".toMediaType() // Corregido el tipo de media para incluir la codificación de caracteres

                // Insertar datos en el servidor
                viewModel.insertarDatosEnServidor(jsonConcatenar)

                // Mostrar el JSON en el Log
                Log.d("JSON", jsonConcatenar)
            }
        }

        // Inflate the layout for this fragment
        return federacionServidoresView
    }



}

