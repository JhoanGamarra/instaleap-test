package com.jhoangamarra.instaleaptest.data.remote

import com.jhoangamarra.instaleaptest.data.model.MovieList
import com.jhoangamarra.instaleaptest.domain.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteMovieDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): MovieList {
        return withContext(Dispatchers.IO) {
           apiService.getAllMovies()
        }
    }

}