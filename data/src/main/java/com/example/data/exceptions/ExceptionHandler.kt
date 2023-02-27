package com.example.data.exceptions

import android.app.Application
import com.example.data.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.koin.core.KoinComponent
import org.koin.core.context.KoinContextHandler
import java.io.InterruptedIOException
import java.lang.reflect.UndeclaredThrowableException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandler : KoinComponent {

    private val app = KoinContextHandler.get().get<Application>()
    private val defaultMessage = app.getString(R.string.default_error_message)
    private val defaultException = AppError(defaultMessage)

    fun handleException(exception: Exception?): AppError {
        val type = when (exception) {
            is ApiException -> //handled exception from api
                "Api"
            is ApiJsonDataException -> //Wrong json structure from api
                "Json structure"
            is SocketTimeoutException, is InterruptedIOException -> //Timeout
                "Time out"
            is retrofit2.HttpException -> {
                "Http Exception ${exception.code()}"
            }
            is NoConnectionException, is UnknownHostException ->
                "No internet"
            is NotKeyStoreException -> //Timeout
                "Not KeyStore"
            else -> //Global exceptions
                "Global $exception"
        }


        val message = when (exception) {
            is JsonSyntaxException -> exception.message
            is retrofit2.HttpException -> {

                val appException = defaultException.copy(exception = exception)
                val errorResponseBody = exception.response()?.errorBody() ?: return appException
                val errorResponseString = errorResponseBody.string()
                val fromJson = try {
                    Gson().fromJson(errorResponseString, AppError::class.java)
                } catch (syntax: JsonSyntaxException) {
                    null
                }
                if (fromJson?.message != null) {
                    return defaultException.copy(
                            message = fromJson.message,
                            exception = exception,
                            body = errorResponseString
                    )
                } else if (exception.message != null) {
                    return defaultException.copy(
                            message = exception.message,
                            exception = exception,
                            body = errorResponseString
                    )
                }
                return defaultException.copy(message = "Http error message", exception = exception, errorResponseString)
            }
            is ApiException -> exception.message
            is NotKeyStoreException -> exception.message
            is ApiJsonDataException -> app.getString(R.string.server_error)
            is JsonSyntaxException -> app.getString(R.string.server_error)
            is NoConnectionException, is UnknownHostException -> app.getString(R.string.no_internet_conntection)
            is SocketTimeoutException, is InterruptedIOException -> app.getString(R.string.time_out_error)
            is UndeclaredThrowableException -> handleException(exception.cause as? Exception).message
            else -> defaultMessage
        }
        return defaultException.copy(message = message, exception = exception)
    }
}