package com.jhoangamarra.instaleaptest.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jhoangamarra.instaleaptest.core.Resource
import com.jhoangamarra.instaleaptest.domain.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun fetchMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(movieRepository.getMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }


}