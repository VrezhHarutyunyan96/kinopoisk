package com.example.kinopoisk.base.fragment

import android.os.Bundle

interface FragmentResultCallback {
    fun onFragmentResult(key: String, result: Bundle)
}