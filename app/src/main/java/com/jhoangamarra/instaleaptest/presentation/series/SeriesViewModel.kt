package com.jhoangamarra.instaleaptest.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jhoangamarra.instaleaptest.core.Resource
import com.jhoangamarra.instaleaptest.domain.MovieRepository
import kotlinx.coroutines.Dispatchers

class SeriesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun fetchSeries() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(movieRepository.getSeries()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }
}