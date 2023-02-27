package com.example.data.remote.service

import com.example.data.model.FilmStaffResponse
import com.example.data.remote.interceptor.ApiConstants.STAFF
import retrofit2.http.GET
import retrofit2.http.Query

interface StaffApiService {

    @GET(STAFF)
    suspend fun getFilmStaff(@Query("filmId") filmId: Int): List<FilmStaffResponse>
}