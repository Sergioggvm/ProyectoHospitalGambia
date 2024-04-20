package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
          "hr": 12444218888
        }
      ]
    }
    """.trimIndent()

            // Iniciar una corrutina para manejar las solicitudes secuencialmente
            CoroutineScope(Dispatchers.IO).launch {
                var fallo = false

                for (pol in pols) {
                    if (usuarioEncontrado != null && usuarioEncontrado.id == pol.book && pol.isSubido.equals("false", ignoreCase = true)) {
                        val jsonConcatenar = "${pol.data.trimEnd('}')},${jsonString2.trimStart('{')}"
                            .trimIndent() // Eliminar espacios en blanco adicionales

                        // Realizar la solicitud y esperar a que se complete antes de continuar
                        val result = viewModel.insertarDatosEnServidorAsync(jsonConcatenar)

                        // Verificar si la solicitud fue exitosa
                        if (!result) {
                            // Si falla alguna solicitud, actualizar la variable de fallo
                            fallo = true
                        } else {
                            // Si la solicitud fue exitosa, actualizar el estado en la base de datos
                            viewModel.actualizarEstadoSubidoEnBD(pol.idPol, "true")
                        }
                    }
                }

                // Mostrar el diálogo apropiado en el hilo principal después de que todas las solicitudes se hayan completado
                withContext(Dispatchers.Main) {
                    if (fallo) {
                        mostrarDialogoPolsNoSubidos()
                    } else {
                        mostrarDialogoPolsSubidos()
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return federacionServidoresView
    }


    private fun mostrarDialogoPolsSubidos() {
        val message = getString(R.string.txt_DatosSubidos)
        val aceptar = getString(R.string.txt_Aceptar)
        val regsitrosEncontrados = getString(R.string.txt_ResgitroDatosSubidos)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(aceptar) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(regsitrosEncontrados)
        alert.show()
    }

    private fun mostrarDialogoPolsNoSubidos() {
        val message = getString(R.string.txt_DatosNoSubidos)
        val aceptar = getString(R.string.txt_Aceptar)
        val regsitrosEncontrados = getString(R.string.txt_ResgitroDatosNoSubidos)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(aceptar) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(regsitrosEncontrados)
        alert.show()
    }


}

