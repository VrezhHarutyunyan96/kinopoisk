package com.example.data.exceptions


data class AppError(val message: String?,
                    val exception: Exception? = null, val body: String? = null) {

    val isNoConnectionException get() = exception is NoConnectionException
}

data class GlobalError(val status: String, val message: String?)



