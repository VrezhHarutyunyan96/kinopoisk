package com.example.kinopoisk.presentation.screen.filmdetails

import android.os.Bundle
import android.view.View
import coil.load
import com.example.domain.model.FilmDetailedUIData
import com.example.domain.model.FilmStaffUIData
import com.example.kinopoisk.base.fragment.BaseVmFragment
import com.example.kinopoisk.base.viewmodel.viewState
import com.example.kinopoisk.databinding.FragmentFilmDetailsBinding
import com.example.kinopoisk.utils.FILM_BUNDLE_KEY
import com.example.kinopoisk.utils.extensions.subscribe


class FilmDetailsFragment : BaseVmFragment<FilmDetailsViewModel, FragmentFilmDetailsBinding>() {

    private val filmStaffAdapter by lazy {
        val adapter = FilmStaffAdapter()
        binding.staffRecycler.adapter = adapter
        return@lazy adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObservers()
    }

    private fun initObservers() = viewModel.run {
        val loadingView = binding.loadingView
        viewState.observe(viewLifecycleOwner) { handleLoadingEvent(it, loadingView) }
        filmDetailsFlow.subscribe(viewLifecycleOwner) { filmDetailedUIData -> buildFilmDetails(filmDetailedUIData) }
        filmStaffFlow.subscribe(viewLifecycleOwner){ staffUIData -> updateRecycler(staffUIData)}
    }

    private fun initViewModel() = viewModel.run {
        val id = arguments?.getInt(FILM_BUNDLE_KEY)
        id?.let {
            getFilmDetails(it)
            getFilmStaff(it)
        }
    }

    private fun updateRecycler(films: List<FilmStaffUIData>?) = binding.run {
        filmStaffAdapter.submitList(films)
    }

    private fun buildFilmDetails(filmDetailedUIData: FilmDetailedUIData) = binding.run {
        filmImage.load(filmDetailedUIData.posterUrl)
        title.text = filmDetailedUIData.nameRu
        year.text = filmDetailedUIData.year
        rating.text = filmDetailedUIData.rating.toString()
        type.text = filmDetailedUIData.type
        voteCount.text = filmDetailedUIData.ratingVoteCount.toString()
        description.text = filmDetailedUIData.description
    }
}