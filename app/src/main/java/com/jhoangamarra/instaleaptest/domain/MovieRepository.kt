package com.jhoangamarra.instaleaptest.domain

import com.jhoangamarra.instaleaptest.data.model.MovieList

interface MovieRepository {

    suspend fun getAllMovies(): MovieList
    suspend fun getMovies(): MovieList
    suspend fun getSeries(): MovieList


}