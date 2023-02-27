package com.example.kinopoisk.presentation.screen.filmdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.domain.model.FilmStaffUIData
import com.example.kinopoisk.base.adapter.BaseBindableListAdapter
import com.example.kinopoisk.databinding.ItemStaffBinding

class FilmStaffAdapter : BaseBindableListAdapter<FilmStaffUIData, ItemStaffBinding>() {

    override fun onBind(binding: ItemStaffBinding, item: FilmStaffUIData, position: Int) {
        binding.run {
            staffImage.load(item.posterUrl)
            name.text = item.nameRu
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ItemStaffBinding =
        ItemStaffBinding.inflate(inflater, parent, false)
}