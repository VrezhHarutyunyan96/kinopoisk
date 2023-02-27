package com.example.data.model


data class FilmsResponse(
    val items: List<FilmData>
)

data class FilmData(
    val kinopoiskId: Int?,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val ratingKinopoisk: Float?,
    val ratingImdb: Float?,
    val year: Int?,
    val type: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val description: String?
)

data class Country(
    val country: String
)

data class Genre(
    val genre: String?
)
