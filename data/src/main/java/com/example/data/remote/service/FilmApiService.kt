package com.example.data.remote.service

import com.example.data.model.FilmDetailedResponse
import com.example.data.model.FilmsResponse
import com.example.data.model.FilmsSearchResponse
import com.example.data.remote.interceptor.ApiConstants.FILMS
import com.example.data.remote.interceptor.ApiConstants.FILMS_BY_KEYWORD
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApiService {

    @GET(FILMS)
    suspend fun getFilms(): FilmsResponse

    @GET(FILMS_BY_KEYWORD)
    suspend fun getFilmsByKeyword(@Query("keyword") keyword: String): FilmsSearchResponse

    @GET("$FILMS/{id}")
    suspend fun getFilmDetails(@Path("id") id: Int): FilmDetailedResponse
}