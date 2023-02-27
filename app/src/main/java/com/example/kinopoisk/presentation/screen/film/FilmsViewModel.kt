package com.example.kinopoisk.presentation.screen.film

import com.example.domain.interactor.FilmInteractor
import com.example.domain.model.FilmUIData
import com.example.kinopoisk.base.viewmodel.BaseViewModel
import com.example.kinopoisk.base.viewmodel.viewState
import com.example.kinopoisk.utils.ViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FilmsViewModel(private val interactor: FilmInteractor) : BaseViewModel() {

    private val _filmsFlow = MutableSharedFlow<List<FilmUIData>?>()
    val filmsFlow = _filmsFlow.asSharedFlow()

    fun getFilms() = launchInIO {
        viewState.postValue(ViewState.Loading)
        val result = interactor.getFilms()
        result?.let { filmUIData -> _filmsFlow.emit(filmUIData) }
        viewState.postValue(ViewState.Complete)
    }

    fun getFilmsByKeyword(keyword: String) = launchInIO {
        viewState.postValue(ViewState.Loading)
        val result = interactor.getFilmsByKeyword(keyword)
        result?.let { filmUIData -> _filmsFlow.emit(filmUIData) }
        viewState.postValue(ViewState.Complete)
    }
}