package com.jhoangamarra.instaleaptest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhoangamarra.instaleaptest.domain.MovieRepository

class MovieViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(MovieRepository::class.java).newInstance((movieRepository))
}