package com.example.kinopoisk.di

import com.example.data.exceptions.ExceptionHandler
import com.example.kinopoisk.presentation.screen.film.FilmsViewModel
import com.example.kinopoisk.presentation.screen.filmdetails.FilmDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FilmsViewModel(get()) }
    viewModel { FilmDetailsViewModel(get()) }
}

val appModule = module(override = true) {

    single {
        ExceptionHandler()
    }
}