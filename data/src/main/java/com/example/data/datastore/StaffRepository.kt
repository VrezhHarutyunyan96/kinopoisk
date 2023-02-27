package com.example.data.datastore

import com.example.data.model.FilmStaffResponse
import retrofit2.http.Query

interface StaffRepository {

    suspend fun getFilmStaff(@Query("filmId") filmId: Int): List<FilmStaffResponse>
}