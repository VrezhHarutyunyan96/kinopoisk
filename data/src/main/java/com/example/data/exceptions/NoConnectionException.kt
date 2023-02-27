package com.example.data.exceptions

import java.io.IOException

class NoConnectionException(override val message: String = "") : IOException(message)