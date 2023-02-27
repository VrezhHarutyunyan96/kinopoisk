package com.example.data.repository

import com.example.data.datastore.FilmRepository
import com.example.data.model.FilmDetailedResponse
import com.example.data.model.FilmsResponse
import com.example.data.remote.service.FilmApiService

class FilmRepositoryImpl(private val filmApiService: FilmApiService) : FilmRepository {

    override suspend fun getFilms() = filmApiService.getFilms()

    override suspend fun getFilmsByKeyword(keyword: String) =
        filmApiService.getFilmsByKeyword(keyword)

    override suspend fun getFilmDetails(id: Int) =
        filmApiService.getFilmDetails(id)

}