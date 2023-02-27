package com.example.domain.model

import com.example.data.model.Country
import com.example.data.model.Genre

data class FilmDetailedUIData(
    val id: Int?,
    val nameRu: String?,
    val nameOriginal: String?,
    val ratingVoteCount: Int?,
    val rating: Float?,
    val webUrl: String?,
    val year: String,
    val filmLength: Int?,
    val description: String?,
    val type: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val posterUrl:String?,
    val posterUrlPreview:String?
)
