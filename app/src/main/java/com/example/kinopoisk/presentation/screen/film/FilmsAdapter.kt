package com.example.kinopoisk.presentation.screen.film

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.domain.model.FilmUIData
import com.example.kinopoisk.base.adapter.BaseBindableListAdapter
import com.example.kinopoisk.databinding.ItemFilmBinding

class FilmsAdapter(val call: (Int?) -> Unit) :
    BaseBindableListAdapter<FilmUIData, ItemFilmBinding>() {

    override fun onBind(binding: ItemFilmBinding, item: FilmUIData, position: Int) {
        binding.run {
            title.text = item.name
            type.text = item.genres?.get(0)?.genre
            rating.text = item.rating.toString()
            filmImage.load(item.posterPreview)
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ItemFilmBinding =
        ItemFilmBinding.inflate(inflater, parent, false)

    override fun onItemClick(item: FilmUIData, position: Int) {
        super.onItemClick(item, position)
        call(item.id)
    }
}