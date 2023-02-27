package com.example.domain.usecase

import com.example.data.datastore.FilmRepository
import com.example.data.datastore.StaffRepository
import com.example.data.model.FilmDetailedResponse
import com.example.data.model.FilmStaffResponse
import com.example.domain.extensions.fromRemoteModelToUI
import com.example.domain.interactor.FilmDetailsInteractor

class FilmDetailsUseCase(
    private val repository: FilmRepository,
    private val staffRepository: StaffRepository
) : FilmDetailsInteractor {

    override suspend fun getFilmDetails(id: Int) =
        getFilmDetailsUIMapper(repository.getFilmDetails(id))

    override suspend fun getFilmStaff(filmId: Int) =
        staffRepository.getFilmStaff(filmId).map { staff -> getFilmStaffUIMapper(staff) }

    override fun getFilmDetailsUIMapper(filmDetails: FilmDetailedResponse) =
        filmDetails.fromRemoteModelToUI()

    override fun getFilmStaffUIMapper(filmStaffResponse: FilmStaffResponse) =
        filmStaffResponse.fromRemoteModelToUI()
}