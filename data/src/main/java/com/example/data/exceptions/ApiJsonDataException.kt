package com.example.data.exceptions

import java.io.IOException

class ApiJsonDataException(override val message: String = "") : IOException(message)