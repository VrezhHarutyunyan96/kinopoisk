package com.example.kinopoisk.navigation

import java.io.IOException

class NoViewAttachedException(message: String) : IOException(message)

class InvalidFragmentTypeException(message: String) : IOException(message)