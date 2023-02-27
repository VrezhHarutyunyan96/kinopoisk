package com.example.domain.model

import com.example.data.model.Country
import com.example.data.model.Genre

data class FilmUIData(
    val id: Int?,
    val name: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val rating: String?,
    val poster: String?,
    val posterPreview: String?,
    val type: String?
)

