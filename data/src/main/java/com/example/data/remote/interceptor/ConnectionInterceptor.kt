package com.example.data.remote.interceptor

import com.example.data.exceptions.NoConnectionException
import com.example.data.remote.manager.NetworkManager
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(private val networkManager: NetworkManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkManager.isConnected) {
            throw NoConnectionException("No Connection")
        }
        return chain.proceed(chain.request())
    }

}