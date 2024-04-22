package com.example.proyectohospitalgambia.core.domain.model.NetworkUtils
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi


class NetworkMonitor(private val context: Context) {
    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun startNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Network is available
                // You can perform your actions here when the network becomes available
            }

            override fun onLost(network: Network) {
                // Network is lost
                // You can perform your actions here when the network is lost
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest,
            networkCallback as ConnectivityManager.NetworkCallback
        )
    }

    fun stopNetworkCallback() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
        }
    }
}
