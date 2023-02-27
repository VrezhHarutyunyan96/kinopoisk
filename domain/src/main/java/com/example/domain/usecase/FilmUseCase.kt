package com.example.domain.usecase

import com.example.data.datastore.FilmRepository
import com.example.data.model.FilmData
import com.example.data.model.FilmSearchData
import com.example.data.model.FilmsResponse
import com.example.domain.extensions.fromRemoteModelToUI
import com.example.domain.interactor.FilmInteractor
import com.example.domain.model.FilmUIData

class FilmUseCase(private val repository: FilmRepository) : FilmInteractor {

    override suspend fun getFilms() =
        getFilmsUIMapper(repository.getFilms().items)

    override suspend fun getFilmsByKeyword(keyword: String) =
        getFilmsSearchUIMapper(repository.getFilmsByKeyword(keyword).films)

    override fun getFilmsUIMapper(films: List<FilmData>) =
        films.map { film -> film.fromRemoteModelToUI() }

    override fun getFilmsSearchUIMapper(films: List<FilmSearchData>) =
        films.map { film -> film.fromRemoteModelToUI() }


}