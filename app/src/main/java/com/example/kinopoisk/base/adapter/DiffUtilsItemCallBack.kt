package com.example.kinopoisk.base.adapter

import androidx.recyclerview.widget.DiffUtil


class DiffUtilsItemCallBack<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {

        return true
    }
}