package com.example.data.model

data class FilmDetailedResponse(
    val kinopoiskId: Int?,
    val nameRu: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingKinopoiskVoteCount: Int?,
    val ratingKinopoisk: Float?,
    val ratingGoodReview: Float?,
    val webUrl: String?,
    val year: String,
    val filmLength: Int?,
    val description: String?,
    val type: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
)