package com.example.kinopoisk.presentation.screen.filmdetails

import com.example.domain.interactor.FilmDetailsInteractor
import com.example.domain.model.FilmDetailedUIData
import com.example.domain.model.FilmStaffUIData
import com.example.kinopoisk.utils.ViewState
import com.example.kinopoisk.base.viewmodel.BaseViewModel
import com.example.kinopoisk.base.viewmodel.viewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FilmDetailsViewModel(private val filmDetailsInteractor: FilmDetailsInteractor) :
    BaseViewModel() {

    private val _filmDetailsFlow = MutableSharedFlow<FilmDetailedUIData>()
    val filmDetailsFlow = _filmDetailsFlow.asSharedFlow()
    private val _filmStaffFlow = MutableSharedFlow<List<FilmStaffUIData>?>()
    val filmStaffFlow = _filmStaffFlow.asSharedFlow()

    fun getFilmDetails(id: Int) = launchInIO {
        viewState.postValue(ViewState.Loading)
        val result = filmDetailsInteractor.getFilmDetails(id)
        result?.let { filmDetailedUIData -> _filmDetailsFlow.emit(filmDetailedUIData) }
        viewState.postValue(ViewState.Complete)
    }

    fun getFilmStaff(id: Int) = launchInIO {
        viewState.postValue(ViewState.Loading)
        val result = filmDetailsInteractor.getFilmStaff(id)
        result?.let { filmStaffUIData -> _filmStaffFlow.emit(filmStaffUIData) }
        viewState.postValue(ViewState.Complete)
    }
}