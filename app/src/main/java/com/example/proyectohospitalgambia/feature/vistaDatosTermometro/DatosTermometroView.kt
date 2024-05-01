package com.example.proyectohospitalgambia.feature.vistaDatosTermometro

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import com.example.proyectohospitalgambia.core.domain.model.termometro.DatosTermometro
import com.example.proyectohospitalgambia.feature.vistaAbout.AboutView
import com.example.proyectohospitalgambia.feature.vistaAjustesConexion.AjustesConexionView
import com.example.proyectohospitalgambia.feature.vistaDatosTensiometro.DatosTensiometroView
import com.example.proyectohospitalgambia.feature.vistaProfile.ProfileView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.math.pow

class DatosTermometroView : AppCompatActivity() {

    private val CCC_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb"
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val bluetoothPermissionRequestCode = 123
    private val deviceName = "FT95"
    private val mac = "FF:00:00:00:07:F5"
    var context = this
    val caracteristica: UUID = UUID.fromString("00002a1c-0000-1000-8000-00805f9b34fb")
    val servicio: UUID = UUID.fromString("00001809-0000-1000-8000-00805f9b34fb")
    private lateinit var bluetoothLeScanner: BluetoothLeScanner
    private val listadispositivos = mutableListOf<BluetoothDevice>()
    private var scanning = false
    private val SCAN_PERIOD: Long = 10000

    private var viewModelJob: Job? = null

    private val viewModel: DatosTermometroViewModel by viewModels()



    // Define una lista para almacenar los registros de datos
    private val datosTermometroList = mutableListOf<DatosTermometro>()

    private lateinit var tv_TemperaturaResultado: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnMedicionTemperatura: ImageButton


    enum class BLELifecycleState {
        Disconnected,
        ConnectedSubscribing,
        Connected
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_datos_termometro_view)

        // Obtener referencia a la barra de herramientas desde el diseño
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_Principal)

        // Inflar el menú en la barra de herramientas
        toolbar.inflateMenu(R.menu.menu_principal)

        // Configurar la barra de herramientas como la barra de soporte de la actividad
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Obtener referencias del layout
        btnMedicionTemperatura = findViewById(R.id.btn_doneTemperatura)
        progressBar = findViewById(R.id.progressBarTemperaturaCargando)
        tv_TemperaturaResultado = findViewById(R.id.tv_TemperaturaResultado)

        val context: Context = this

        // Configurar el evento clic del botón
        btnMedicionTemperatura.setOnClickListener {
            // Verificar permisos y solicitarlos si es necesario
            val hasBluetoothPermissions = (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH
            ) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADMIN
            ) == PackageManager.PERMISSION_GRANTED)
            val hasLocationPermissions = (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
            val hasBluetoothScanPermissions = (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED)
            val hasBluetoothConnectPermissions = (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                if (!hasBluetoothPermissions || !hasLocationPermissions || !hasBluetoothScanPermissions || !hasBluetoothConnectPermissions) {
                    Log.d("Bluetooth2", "Permisos denegados")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ), 1
                    )
                    return@setOnClickListener
                }
            } else {
                if (!hasBluetoothPermissions) {
                    Log.d("Bluetooth2", "Permisos denegados")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN
                        ), 1
                    )
                    return@setOnClickListener
                }
            }


            // Permisos concedidos
            Log.d("Bluetooth2", "Permisos concedidos")
            val bluetoothManager =
                context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothAdapter = bluetoothManager.adapter

            // Si los permisos ya están concedidos, iniciar la búsqueda de dispositivos
            startDeviceSearch(
                this, ""
            )

            // Mostrar la ProgressBar y quitar el boton
            progressBar.visibility = View.VISIBLE
            btnMedicionTemperatura.visibility = View.INVISIBLE

            // Iniciar la corrutina para simular el proceso de carga
            viewModelJob = CoroutineScope(Dispatchers.Main).launch {
                // Simular el progreso de carga durante 3 segundos
                for (progress in 0..100) {
                    progressBar.progress = progress
                    delay(150) // Cambia este valor para ajustar la velocidad de llenado de la ProgressBar
                }

                // Ocultar la ProgressBar
                progressBar.visibility = View.INVISIBLE
                btnMedicionTemperatura.visibility = View.VISIBLE

                cargarRegistros()

                // Llamar a la función obtenerDatosFormulario POLS
                val usuarioActivo = MainActivity.usuario
                val datosFormulario = obtenerDatosFormulario()

                if (datosFormulario != null) {
                    val idPols = generarIdAleatorio()
                    val idBook = MainActivity.usuario?.id.toString()

                    val pol = Pol(idPols, idBook, datosFormulario.toString(), "false")

                    if (usuarioActivo != null) {
                        usuarioActivo.pols.add(pol)
                    }

                    // Llamar al método del ViewModel para insertar datos
                    var resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                }

            }

        }

    }

    private fun mostrarDialogoSalir() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.txt_MensajeTituloSalirAplicacion))
        builder.setMessage(getString(R.string.txt_MensajeSalirAplicacion))
        builder.setNegativeButton(getString(R.string.txt_No)) { dialog, which ->
            // Si el usuario elige no salir, simplemente cerramos el diálogo
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.txt_Si)) { dialog, which ->
            // Si el usuario elige salir, cerramos la actividad y, por lo tanto, la aplicación
            finishAffinity()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun cargarRegistros() {

        // Bucle forEach para recorrer todos los registros y encontrar el más reciente
        var ultimoDatoTermometro: DatosTermometro? = null
        datosTermometroList.forEach { record ->
            if (ultimoDatoTermometro == null || record.fechaHora > ultimoDatoTermometro!!.fechaHora) {
                ultimoDatoTermometro = record
            }
        }

        // Verificar si se encontró algún registro con fecha y hora
        if (ultimoDatoTermometro != null) {
            Log.d(
                "Bluetooth2",
                "Registro con la última fecha y hora: ${ultimoDatoTermometro!!.fechaHoraFormatted}"
            )
            Log.d("Bluetooth2", "Datos - Temperatura: ${ultimoDatoTermometro!!.temperatura}")

            // Se ejecuta en el hilo principal porque si njo
            runOnUiThread {
                tv_TemperaturaResultado.text = ultimoDatoTermometro!!.temperatura.toString()
                Log.d("TEMPERATURANUMERO", ultimoDatoTermometro!!.temperatura.toString())
            }
            mostrarDialogoDatosEncontrados()

            // Eliminar todos los elementos de la lista
            datosTermometroList.clear()


        } else {
            Log.d("Bluetooth2", "No se encontraron registros.")

            mostrarDialogoDatosNoEncontrados()
            tv_TemperaturaResultado.text = "0"

        }
    }

    // Función para mostrar un diálogo cuando se encuentran registros
    private fun mostrarDialogoDatosEncontrados() {
        val message = getString(R.string.txt_DatosEncontrados)
        val aceptar = getString(R.string.txt_Aceptar)
        val regsitrosEncontrados = getString(R.string.txt_RegsitrosEncontrados)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(aceptar) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(regsitrosEncontrados)
        alert.show()
    }

    // Función para mostrar un diálogo cuando no se encuentran registros
    private fun mostrarDialogoDatosNoEncontrados() {
        val message = getString(R.string.txt_DatosNoEncontrados)
        val aceptar = getString(R.string.txt_Aceptar)
        val regsitrosNoEncontrados = getString(R.string.txt_RegsitrosNoEncontrados)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(aceptar) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(regsitrosNoEncontrados)
        alert.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Verificar si la solicitud de permisos es para BLUETOOTH
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            // Si los permisos fueron concedidos, iniciar la búsqueda de dispositivos
            val deviceName = "BC54"
            val context: Context = this
            startDeviceSearch(context, deviceName)
        }
    }

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_ADMIN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                    ), 1
                )
            }
            Log.d("Bluetooth2", "Device found: ${result.device.name}")
            if (result.device.name != null && result.device.name.equals(deviceName)) {
                listadispositivos.add(result.device)
            }

        }
    }

    private suspend fun scanLeDevice(): List<BluetoothDevice> {
        val devicesFound = mutableListOf<BluetoothDevice>()
        withContext(Dispatchers.IO) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_ADMIN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                    ), 1
                )
            }
            if (!scanning) {
                bluetoothLeScanner.startScan(leScanCallback)
                scanning = true
                delay(SCAN_PERIOD)
                bluetoothLeScanner.stopScan(leScanCallback)
                scanning = false
                devicesFound.addAll(listadispositivos)
                listadispositivos.clear() // Limpiar la lista después de escanear
            }
        }
        return devicesFound
    }

    // Llamar a scanLeDevice dentro de un bloque coroutine
    private fun startDeviceSearch(context: Context, deviceName: String) {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            Log.d("Bluetooth2", "Bluetooth is not enabled")
            return
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADMIN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN
                ), 1
            )
        }

        // Llamar a scanLeDevice dentro de un bloque coroutine
        CoroutineScope(Dispatchers.Main).launch {
            val devices = scanLeDevice()
            if (devices.isNotEmpty()) {
                connectToDevice(devices[0])
            } else {
                Log.d("Bluetooth2", "Device not found")
            }
        }
    }


    private var lifecycleState = DatosTermometroView.BLELifecycleState.Disconnected
        set(value) {
            field = value
            Log.d("Bluetooth2", "Lifecycle state: $value")

        }


    private fun connectToDevice(device: BluetoothDevice) {
        // Check if we have the necessary permissions
        if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
        ) {
            // If we don't have the permissions, request them from the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN),
                bluetoothPermissionRequestCode
            )
            return
        }

        // Now that we have the permissions, proceed with the Bluetooth connection
        val gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH
                        ) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_ADMIN
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN
                            ), 1
                        )
                    }
                    Log.d("Bluetooth2", "Connected to ${device.name}")
                    gatt?.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d("Bluetooth2", "Disconnected from ${device.name}")
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH
                    ) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_ADMIN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN
                        ), 1
                    )
                }
                Log.d("Bluetooth2", "Services discovered")


                val characteristic = gatt?.getService(servicio)?.getCharacteristic(caracteristica)
                Log.d("Bluetooth2", characteristic?.uuid.toString())
                gatt?.setCharacteristicNotification(characteristic, true)
                gatt?.readCharacteristic(characteristic)
                characteristic?.let {
                    lifecycleState = DatosTermometroView.BLELifecycleState.ConnectedSubscribing
                    subscribeToIndications(it, gatt)
                } ?: run {
                    lifecycleState = DatosTermometroView.BLELifecycleState.Connected
                }

            }


            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic
            ) {
                // Handle the characteristic change
                Log.d("Bluetooth2", "Datos de la característica cambiados")
                if (characteristic.uuid == UUID.fromString("00002a1c-0000-1000-8000-00805f9b34fb")) {
                    // Obtener los datos de la característica
                    val data = characteristic.value
                    if (data != null && data.size >= 13) {
                        // Interpretar los datos recibidos
                        val flags = data[0]
                        val temperatureBytes = data.copyOfRange(1, 5)
                        val timestampBytes = data.copyOfRange(5, 12)
                        val temperature = convertToTemperature(temperatureBytes)
                        val year = ByteBuffer.wrap(timestampBytes.copyOfRange(0, 2))
                            .order(ByteOrder.LITTLE_ENDIAN).short.toInt()
                        val month = timestampBytes[2].toInt()
                        val day = timestampBytes[3].toInt()
                        val hour = timestampBytes[4].toInt()
                        val minute = timestampBytes[5].toInt()
                        val second = timestampBytes[6].toInt()

                        val calendar = Calendar.getInstance()
                        calendar.set(year, month - 1, day, hour, minute, second)

                        val fechaHora = calendar.time

                        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        val fechaFormateada = sdf.format(fechaHora)

                        // Crear un objeto DataRecord con los datos recibidos
                        val dataRecord = DatosTermometro(fechaHora, temperature)

                        // Si no existe, agregar un nuevo registro a la lista
                        datosTermometroList.add(dataRecord)
                        // Logs adicionales para temperatura y fecha
                        Log.d("Bluetooth2_Temperatura", "Temperatura: $temperature")
                        Log.d("Bluetooth2_Fecha", "Fecha y Hora: $fechaFormateada")

                        Log.d(
                            "Bluetooth2",
                            "Temperatura: $temperature, Fecha y Hora: $fechaFormateada"
                        )
                    } else {
                        Log.e("Bluetooth2", "Datos de característica inválidos")
                    }
                }
            }

            fun convertToTemperature(data: ByteArray): Double {
                // Invertir el array de datos
                val reversedData = data.reversedArray()

                // Extraer el exponente y la mantisa
                val exponent = reversedData[0].toInt()
                val mantissa =
                    reversedData.sliceArray(1 until reversedData.size).map { it.toInt() and 0xFF }
                        .fold(0) { acc, byte ->
                            (acc shl 8) + byte
                        }
                // Calcular la temperatura
                val temperature = mantissa * 10.0.pow(exponent.toDouble())
                return temperature
            }
        }

        // Check the permissions again before connecting the device
        if (checkSelfPermission(Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
        ) {
            // Proceed with the Bluetooth connection
            device.connectGatt(this, false, gattCallback)
        } else {
            Log.e(
                "Bluetooth2",
                "The necessary permissions to connect to the Bluetooth device were not granted."
            )
        }
    }

    private fun subscribeToIndications(
        characteristic: BluetoothGattCharacteristic,
        gatt: BluetoothGatt
    ) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADMIN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN
                ), 1
            )
        }

        val cccdUuid = UUID.fromString(CCC_DESCRIPTOR_UUID)
        characteristic.getDescriptor(cccdUuid)?.let { cccDescriptor ->
            if (!gatt.setCharacteristicNotification(characteristic, true)) {
                return
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                gatt.writeDescriptor(cccDescriptor, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)
            } else {
                cccDescriptor.value = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
                gatt.writeDescriptor(cccDescriptor)
            }
        }
    }


    //Menú de opciones
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.mn_menu -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, MainActivity::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)

                true
            }

            R.id.mn_perfil -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, ProfileView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)

                true
            }

            R.id.mn_conexion -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, AjustesConexionView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
                true
            }

            R.id.mn_acercaDe -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, AboutView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
                true
            }

            R.id.mn_datosTermometro -> {


                true
            }

            R.id.mn_datosTensiometro -> {
                // Creamos un Intent para iniciar VistaSeleccionPartida.
                val intent = Intent(this, DatosTensiometroView::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                // Iniciamos la actividad sin esperar un resultado.
                startActivity(intent)
                true
            }

            R.id.mn_salir -> {
                mostrarDialogoSalir()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    // Método para obtener los datos del formulario y crear el JSON
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener el valor del TextView
        Log.d("obtenerDatosFormulario111111", "Método llamado")
        val temperaturaText = tv_TemperaturaResultado.text.toString()

        // Verificar si el campo está vacío
        if (temperaturaText.isEmpty()) {
            Log.d("ObtenerDatosFormulario111111", "Temperatura vacía")
            return null // Devolver null si el campo está vacío
        }

        try {
            // Convertir el valor a tipo numérico
            Log.d("TemperaturaTexto111111", temperaturaText)
            val temperatura = temperaturaText.toDouble()
            val temperaturaConUnidad = String.format("%.1f", temperatura) + " °C"
            tv_TemperaturaResultado.text = temperaturaConUnidad

            Log.d("Temperatura111111", String.format("%.1f", temperatura)) // Aquí se modifica para imprimir solo un decimal

            // Obtener la fecha y hora actual
            val currentDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())


            if (temperatura != 0.0){
                // Crear el objeto JSON con los datos del formulario
                val jsonObject = JSONObject()
                jsonObject.put("TipoPol", "temperatura")
                jsonObject.put("FechaInsercion", currentDateAndTime)
                jsonObject.put("temperatura", temperatura)

                // Mostrar el JSON en el Log
                Log.d("JSON Data11111", jsonObject.toString())

                return jsonObject
            }

            return null
        } catch (e: NumberFormatException) {
            Log.e("Convertir a Double", "Error: ${e.message}")
            return null
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModelJob?.cancel() // Cancela la corrutina cuando se destruye la actividad
        progressBar.visibility = View.INVISIBLE
        btnMedicionTemperatura.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        viewModelJob?.cancel() // Cancela la corrutina cuando la actividad entra en pausa
        progressBar.visibility = View.INVISIBLE
        btnMedicionTemperatura.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        viewModelJob?.cancel() // Cancela la corrutina cuando la actividad entra en pausa
        progressBar.visibility = View.INVISIBLE
        btnMedicionTemperatura.visibility = View.VISIBLE
    }


}