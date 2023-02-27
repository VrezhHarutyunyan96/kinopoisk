package com.example.data.repository

import com.example.data.datastore.StaffRepository
import com.example.data.remote.service.StaffApiService

class StaffRepositoryImpl(private val staffApiService: StaffApiService) : StaffRepository {

    override suspend fun getFilmStaff(filmId: Int) =
        staffApiService.getFilmStaff(filmId)

}