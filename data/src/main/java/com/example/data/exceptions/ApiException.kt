package com.example.data.exceptions

import java.io.IOException

class ApiException(override val message: String = "") : IOException(message)