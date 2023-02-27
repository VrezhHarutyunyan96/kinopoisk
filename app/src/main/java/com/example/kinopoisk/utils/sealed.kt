package com.example.kinopoisk.utils

sealed class ViewState {

    object Idle : ViewState()
    object Loading : ViewState()
    object Complete : ViewState()
    object Invisible : ViewState()
    object Visible : ViewState()
    class Error(val message: String) : ViewState()
}
