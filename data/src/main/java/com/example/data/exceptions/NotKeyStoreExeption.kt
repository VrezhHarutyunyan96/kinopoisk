package com.example.data.exceptions

import java.io.IOException

class NotKeyStoreException(override val message: String = "") : IOException(message)

