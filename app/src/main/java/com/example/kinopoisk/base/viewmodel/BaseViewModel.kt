package com.example.kinopoisk.base.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.exceptions.AppError
import com.example.data.exceptions.ExceptionHandler
import com.example.data.exceptions.GlobalError
import com.example.data.model.ErrorResponse
import com.example.kinopoisk.utils.ServerType
import com.example.kinopoisk.utils.ViewState
import com.example.kinopoisk.utils.extensions.posValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import java.io.StringReader
import java.lang.reflect.Type


typealias ViewStateLiveData = MutableLiveData<ViewState>

var viewState = ViewStateLiveData(ViewState.Complete)

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val globalErrorLiveFlow = MutableSharedFlow<String?>()
    private val bgContext = Dispatchers.IO
    private val mainContext = Dispatchers.Main
    private val exceptionHandler by inject<ExceptionHandler>()


    protected fun <T> launchInIO(
        handleException: Boolean = true,
        cancelPreviews: Boolean = false,
        job: CompletableJob = Job(),
        serverType: ServerType = ServerType.MAIN,
        block: suspend CoroutineScope.() -> T
    ): Job {
        return viewModelScope.launch(job) {
            withIOContext(handleException, block)
        }
    }


    protected fun <T> launch(block: suspend CoroutineScope.() -> T): Job {
        return viewModelScope.launch(mainContext) {
            block()
        }
    }

    protected suspend fun withMainContext(block: () -> Unit) {
        withContext(mainContext) {
            block()
        }
    }

    protected open fun onError(appError: AppError) {

        if (appError.exception is HttpException) {
            val code = (appError.exception as HttpException).code()
            if (code == 401 || code == 402 || code == 404 || code == 429) {
                globalErrorLiveFlow.posValue(viewModelScope, appError.message)
                return
            }
            val fromJson = Gson().fromJson(appError.body, GlobalError::class.java)
            if (fromJson?.message != null) {
                globalErrorLiveFlow.posValue(viewModelScope, fromJson.message)
                return
            }

        } else {
            globalErrorLiveFlow.posValue(viewModelScope, appError.message)
        }
    }

    private suspend fun <T> withIOContext(
        handleException: Boolean = true,
        block: suspend CoroutineScope.() -> T
    ) {
        try {
            withContext(bgContext, block)
        } catch (exception: Exception) {
            Log.v("Response exeption", exception.message.toString())
            if (!handleException) {
                return
            }
            if (exception !is CancellationException) {
                onError(exception)
            }
        }
    }

    private fun onError(exception: Exception) {
        val appError = exceptionHandler.handleException(exception)
        if (appError.exception !is CancellationException) {
            onError(appError)
        }
    }

    protected suspend inline fun <reified T> asyncDeferred(deferred: Deferred<T>, defaultValue: T) =
        try {
            deferred.await()
        } catch (e: Exception) {
            println("Handle $e in try/catch")
            defaultValue
        }


    inline fun <reified T> handleError(appError: AppError, crossinline block: suspend (T) -> Unit) {
        val wrapedType: Type = object : TypeToken<ErrorResponse<T>>() {}.type
        val type: Type = object : TypeToken<T>() {}.type
        if (appError.body != null) {
            val newJsonReader = Gson().newJsonReader(StringReader(appError.body))
            val fromJson = Gson().fromJson<ErrorResponse<T>>(newJsonReader, wrapedType)
            if (fromJson is ErrorResponse) {
                val error = Gson().fromJson<T>(Gson().toJson(fromJson.errors), type)
                if (error != null) {
                    viewModelScope.launch {
                        block(error)
                    }
                }
            }
        }
    }

    fun clearCall() {
        bgContext.cancel()
    }
}