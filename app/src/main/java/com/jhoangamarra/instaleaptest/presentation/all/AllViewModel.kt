package com.jhoangamarra.instaleaptest.presentation.all

import androidx.lifecycle.*
import com.jhoangamarra.instaleaptest.core.Resource
import com.jhoangamarra.instaleaptest.domain.MovieRepository
import kotlinx.coroutines.Dispatchers

class AllViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun fetchAllMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(movieRepository.getAllMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }
}

