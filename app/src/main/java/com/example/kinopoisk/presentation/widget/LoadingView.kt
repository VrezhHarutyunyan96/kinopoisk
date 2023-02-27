package com.example.kinopoisk.presentation.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.LoadingIndicatorViewBinding
import com.example.kinopoisk.utils.extensions.dpToPx

class LoadingView(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {

    private val binding: LoadingIndicatorViewBinding

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.loading_indicator_view, this, true)
        binding = LoadingIndicatorViewBinding.bind(root)
        isClickable = true
        isFocusable = true
        elevation = dpToPx(10).toFloat()
        translationZ = dpToPx(10).toFloat()
        isVisible = false
    }

    fun changeLoadingStatus(isVisible: Boolean) {
        if (isVisible) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    fun showLoading(dimmed: Boolean = false) {
        setBackgroundColor(Color.TRANSPARENT)
        isVisible = true
        binding.indicatorView.isVisible = true
        bringToFront()
    }

    fun hideLoading() {
        binding.indicatorView.isVisible = false
        isVisible = false
    }
}

