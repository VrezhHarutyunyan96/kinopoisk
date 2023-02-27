package com.example.data.remote.interceptor

import com.example.data.remote.interceptor.ApiConstants.API_KEY
import com.example.data.remote.interceptor.base.PublicInterceptor
import okhttp3.Request

class PublicHeaderInterceptor : PublicInterceptor() {

    override fun intercept(requestBuilder: Request.Builder): Request.Builder {
        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("X-API-KEY", API_KEY)

        return requestBuilder
    }
}