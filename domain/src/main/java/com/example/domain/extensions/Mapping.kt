package com.example.domain.extensions

import com.example.data.model.*
import com.example.domain.model.FilmDetailedUIData
import com.example.domain.model.FilmStaffUIData
import com.example.domain.model.FilmUIData

fun FilmData.fromRemoteModelToUI() = FilmUIData(
    id = kinopoiskId,
    name = nameRu,
    countries = countries,
    genres = genres,
    rating = ratingKinopoisk.toString(),
    poster = posterUrl,
    posterPreview = posterUrlPreview,
    type = type
)

fun FilmSearchData.fromRemoteModelToUI() = FilmUIData(
    id = filmId,
    name = nameRu,
    countries = countries,
    genres = genres,
    rating = rating,
    poster = posterUrl,
    posterPreview = posterUrlPreview,
    type = type
)

fun FilmDetailedResponse.fromRemoteModelToUI() = FilmDetailedUIData(
    id = kinopoiskId,
    nameRu = nameRu,
    nameOriginal = nameOriginal,
    ratingVoteCount = ratingKinopoiskVoteCount,
    rating = ratingKinopoisk,
    webUrl = webUrl,
    year = year,
    filmLength = filmLength,
    description = description,
    type = type,
    countries = countries,
    genres = genres,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview
)

fun FilmStaffResponse.fromRemoteModelToUI() = FilmStaffUIData(
    id = staffId,
    nameEn = nameEn,
    nameRu = nameRu,
    description = description,
    posterUrl = posterUrl,
    professionText = professionText
)