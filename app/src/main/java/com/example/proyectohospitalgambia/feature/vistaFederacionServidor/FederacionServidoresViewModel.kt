package com.example.proyectohospitalgambia.feature.vistaFederacionServidor

import androidx.lifecycle.ViewModel
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Random

/**
 * Clase FederacionServidoresViewModel.
 *
 * Esta clase representa el ViewModel para la vista de la federación de servidores en la aplicación.
 *
 * @property jsonMediaType Tipo de medio para el formato JSON.
 * @property client Cliente HTTP para realizar solicitudes a la red.
 * @property databaseHelper Ayudante de la base de datos para realizar operaciones de base de datos.
 *
 * @method generarIdAleatorio Método para generar un ID aleatorio.
 * @method insertarDatosEnServidorAsync Método para insertar datos en el servidor de forma asíncrona.
 * @method recuperarDatos Método para recuperar datos de la base de datos.
 * @method actualizarEstadoSubidoEnBD Método para actualizar el estado de subida de un Pol en la base de datos.
 */
class FederacionServidoresViewModel : ViewModel() {


    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient()

    private var databaseHelper: DatabaseHelper = MainActivity.databaseHelper!!

    /**
     * Genera un ID aleatorio.
     *
     * @return String El ID aleatorio generado.
     */
    private fun generarIdAleatorio(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val id = StringBuilder()
        for (i in 0 until 10) {
            id.append(caracteres[random.nextInt(caracteres.length)])
        }
        return id.toString()
    }

    /**
     * Inserta los datos en el servidor de forma asíncrona.
     *
     * @param polJsonString La cadena JSON del Pol a insertar.
     * @return Boolean Indica si la inserción fue exitosa o no.
     */
    fun insertarDatosEnServidorAsync(polJsonString: String): Boolean {
        return runBlocking {
            try {
                val idPagina = generarIdAleatorio()
                val url = MainActivity.url + "/pols/ESPGNU777ORG/" + idPagina

                val request = Request.Builder()
                    .url(url)
                    .post(polJsonString.toRequestBody(jsonMediaType))
                    .header("Authorization", Credentials.basic("ESPGNU777ORG", "gnusolidario"))
                    .build()

                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                if (!response.isSuccessful) {
                    println("Error al enviar la solicitud: ${response.message}")
                    return@runBlocking false
                }

                val resultado = response.body?.string()
                val fallo = resultado == null || resultado.equals("null", ignoreCase = true)
                return@runBlocking !fallo
            } catch (e: IllegalArgumentException) {
                // Captura la excepción y muestra un mensaje de error
                println("Error al construir la URL: ${e.message}")
                e.printStackTrace() // Imprime la traza de la excepción en Logcat
                return@runBlocking false
            } catch (e: Exception) {
                // Captura la excepción y muestra un mensaje de error
                println("Error al construir la URL: ${e.message}")
                e.printStackTrace() // Imprime la traza de la excepción en Logcat
                return@runBlocking false
            }
        }
    }

    /**
     * Recupera los datos de la base de datos.
     *
     * @return List<Pol> La lista de Pols recuperados de la base de datos.
     */
    fun recuperarDatos(): List<Pol> {
        return databaseHelper.obtenerPols()
    }

    /**
     * Actualiza el estado de subida de un Pol en la base de datos.
     *
     * @param idPol El ID del Pol a actualizar.
     * @param nuevoEstado El nuevo estado de subida del Pol.
     * @return Boolean Indica si la actualización fue exitosa o no.
     */
    fun actualizarEstadoSubidoEnBD(idPol: String, nuevoEstado: String): Boolean {
        return try {
            databaseHelper.actualizarEstadoSubido(idPol, nuevoEstado)
        } catch (e: Exception) {

            false
        }
    }

}