package com.example.kinopoisk.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kinopoisk.R
import com.example.kinopoisk.navigation.presentFragment
import com.example.kinopoisk.presentation.screen.film.FilmsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presentFragment<FilmsFragment>(backStack = false)
    }
}