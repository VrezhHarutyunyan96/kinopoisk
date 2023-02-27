package com.example.domain.di

import com.example.domain.interactor.FilmDetailsInteractor
import com.example.domain.interactor.FilmInteractor
import com.example.domain.usecase.FilmDetailsUseCase
import com.example.domain.usecase.FilmUseCase
import org.koin.dsl.module

val interactorModule = module {
    single<FilmInteractor> { FilmUseCase(get()) }
    single<FilmDetailsInteractor> { FilmDetailsUseCase(get(), get()) }
}