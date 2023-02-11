package com.ghartakshiksha.network.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.ghartakshiksha.utility.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")
        return chain.proceed(chain.request())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable(): Boolean {
        var result = false
        try {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            connectivityManager?.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                                (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true
                        else -> false
                    }
                }
            }
        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        }
        return result
    }

}