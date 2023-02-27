package com.example.domain.interactor

import com.example.data.model.FilmData
import com.example.data.model.FilmSearchData
import com.example.data.model.FilmsResponse
import com.example.domain.model.FilmUIData

interface FilmInteractor {

    suspend fun getFilms(): List<FilmUIData>?

    suspend fun getFilmsByKeyword(keyword: String): List<FilmUIData>?

    fun getFilmsUIMapper(films: List<FilmData>): List<FilmUIData>

    fun getFilmsSearchUIMapper(films: List<FilmSearchData>): List<FilmUIData>
}