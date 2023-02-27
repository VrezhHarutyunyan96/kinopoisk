package com.example.domain.interactor

import com.example.data.model.FilmDetailedResponse
import com.example.data.model.FilmStaffResponse
import com.example.domain.model.FilmDetailedUIData
import com.example.domain.model.FilmStaffUIData

interface FilmDetailsInteractor {

    suspend fun getFilmDetails(id: Int): FilmDetailedUIData?

    suspend fun getFilmStaff(filmId: Int): List<FilmStaffUIData>?

    fun getFilmDetailsUIMapper(filmDetails: FilmDetailedResponse): FilmDetailedUIData

    fun getFilmStaffUIMapper(filmStaffResponse: FilmStaffResponse): FilmStaffUIData
}