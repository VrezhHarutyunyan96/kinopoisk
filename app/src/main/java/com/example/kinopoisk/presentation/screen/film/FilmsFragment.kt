package com.example.kinopoisk.presentation.screen.film

import android.os.Bundle
import android.view.View
import com.example.domain.model.FilmUIData
import com.example.kinopoisk.base.fragment.BaseVmFragment
import com.example.kinopoisk.base.viewmodel.viewState
import com.example.kinopoisk.databinding.FragmentFilmsBinding
import com.example.kinopoisk.navigation.presentFragment
import com.example.kinopoisk.presentation.screen.filmdetails.FilmDetailsFragment
import com.example.kinopoisk.utils.*
import com.example.kinopoisk.utils.extensions.hideKeyBoard
import com.example.kinopoisk.utils.extensions.onClick
import com.example.kinopoisk.utils.extensions.subscribe

class FilmsFragment : BaseVmFragment<FilmsViewModel, FragmentFilmsBinding>() {

    private val filmsAdapter by lazy {
        val adapter = FilmsAdapter { filmId -> openFilmDetailPage(filmId) }
        binding.filmRecycler.adapter = adapter
        return@lazy adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        initViewModel()
    }

    private fun initListeners() = binding.run {
        searchButton.onClick { makeSearch(searchText.text.toString()) }
    }

    private fun initViewModel() = viewModel.run {
        getFilms()
    }

    private fun initObservers() = viewModel.run {
        val loadingView = binding.loadingView
        viewState.observe(viewLifecycleOwner) { handleLoadingEvent(it, loadingView) }
        filmsFlow.subscribe(viewLifecycleOwner) { updateRecycler(it) }
    }

    private fun updateRecycler(films: List<FilmUIData>?) = binding.run {
        filmsAdapter.submitList(films)
    }

    private fun makeSearch(searchText: String) {
        viewModel.getFilmsByKeyword(searchText)
        activity?.hideKeyBoard()
    }

    private fun openFilmDetailPage(id: Int?) {
        val args = FILM_BUNDLE_KEY to id
        presentFragment<FilmDetailsFragment>(arguments = arrayOf(args))
    }


}