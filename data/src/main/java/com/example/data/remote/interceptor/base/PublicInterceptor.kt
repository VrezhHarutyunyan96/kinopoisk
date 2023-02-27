package com.example.data.remote.interceptor.base

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class PublicInterceptor : Interceptor {

    final override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newBuilder = intercept(request.newBuilder())
        return chain.proceed(newBuilder.build())
    }

    abstract fun intercept(requestBuilder: Request.Builder): Request.Builder
}