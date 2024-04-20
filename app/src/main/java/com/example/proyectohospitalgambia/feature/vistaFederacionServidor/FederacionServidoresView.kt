package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.core.network.federeacionServidorApi
import org.json.JSONObject

import com.example.proyectohospitalgambia.app.retrofit.RetrofitClient
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

        // Crear una instancia del servicio de la API
        val apiService = RetrofitClient.instance.create(federeacionServidorApi::class.java)

        // Configurar el OnClickListener para el botón btnRecargaServidor
        btnRecargaServidor.setOnClickListener {
            val jsonMediaType = "application/json; charset=utf-8".toMediaType() // Corregido el tipo de media para incluir la codificación de caracteres
            val jsonString = """
        
      {"TipoPol":"Peso","FechaInsercion":"2024-04-18 19:16:30","Osat":1,
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
          "hr": 12455544
        }
      ]
      
    }
    """.trimIndent()

            viewModel.insertarDatosEnServidor(jsonString)

        }

        // Inflate the layout for this fragment
        return federacionServidoresView
    }


}

