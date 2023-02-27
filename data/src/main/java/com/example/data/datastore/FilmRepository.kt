package com.example.data.datastore

import com.example.data.model.FilmDetailedResponse
import com.example.data.model.FilmsResponse
import com.example.data.model.FilmsSearchResponse

interface FilmRepository {

    suspend fun getFilms(): FilmsResponse

    suspend fun getFilmsByKeyword(keyword: String): FilmsSearchResponse

    suspend fun getFilmDetails(id: Int): FilmDetailedResponse
}