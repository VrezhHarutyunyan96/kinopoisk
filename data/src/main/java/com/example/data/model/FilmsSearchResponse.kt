package com.example.data.model

data class FilmsSearchResponse(
    val films: List<FilmSearchData>
)

data class FilmSearchData(
    val filmId: Int?,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val rating: String?,
    val ratingImdb: Float?,
    val year: Int?,
    val type: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val description: String?
)
