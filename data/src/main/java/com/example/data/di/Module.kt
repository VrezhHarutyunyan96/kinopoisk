package com.example.data.di

import com.example.data.datastore.FilmRepository
import com.example.data.datastore.StaffRepository
import com.example.data.remote.interceptor.ApiConstants
import com.example.data.remote.interceptor.ConnectionInterceptor
import com.example.data.remote.interceptor.PublicHeaderInterceptor
import com.example.data.remote.manager.NetworkManager
import com.example.data.remote.service.FilmApiService
import com.example.data.remote.service.StaffApiService
import com.example.data.repository.FilmRepositoryImpl
import com.example.data.repository.StaffRepositoryImpl
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule
    get() = repoModule + apiModule

val apiModule
    get() = interceptorModule + retrofitModule + serviceModule

private val repoModule = module {
    single<FilmRepository> { FilmRepositoryImpl(get()) }
    single<StaffRepository> { StaffRepositoryImpl(get()) }
}

private val serviceModule = module {
    single { get<Retrofit>().create(FilmApiService::class.java) }
    single { get<Retrofit>().create(StaffApiService::class.java) }
}


private const val TIMEOUT = 20L

private val interceptorModule = module {

    single {
        PublicHeaderInterceptor()
    }

    single {
        ConnectionInterceptor(get())
    }
}

private val retrofitModule = module {

    single {
        NetworkManager(androidApplication())
    }

    single {
        val okHttpBuilder = OkHttpClient.Builder()

        with(okHttpBuilder) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(get<PublicHeaderInterceptor>())
            addInterceptor(get<ConnectionInterceptor>())

            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            callTimeout(TIMEOUT, TimeUnit.SECONDS)
        }
        okHttpBuilder
    }

    single {
        val gsonBuilder = GsonBuilder()
            .setLenient()
        val okHttp = get<OkHttpClient.Builder>()
            .build()

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(okHttp)
    }

    factory {
        get<Retrofit.Builder>()
            .baseUrl(ApiConstants.BASE_URL)
            .build()
    }
}
