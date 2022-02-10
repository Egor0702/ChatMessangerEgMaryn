package com.example.chatmessangeregmaryn.remote.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = networkInfo()
    init {
        Log.d("Egor", "Всем хло, мы в NetworkHandler")
    }


    private fun networkInfo(): Boolean {
        Log.d("Egor", "NetworkHandler networkInfo()")
        val connectivityManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                   return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            if(networkInfo != null && networkInfo.isConnected)
            return true
        }
        return false
    }
}

