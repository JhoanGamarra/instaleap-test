package com.jhoangamarra.instaleaptest.data.local

import com.jhoangamarra.instaleaptest.data.model.MovieEntity
import com.jhoangamarra.instaleaptest.data.model.MovieList
import com.jhoangamarra.instaleaptest.data.model.toMovieList
import com.jhoangamarra.instaleaptest.utils.AppConstants

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getAllMovies(): MovieList = movieDao.getAllMovies().toMovieList()

    suspend fun getMovies(): MovieList  = movieDao.getAllMovies().filter { movie -> movie.type == AppConstants.MOVIE_TYPE }.toMovieList()

    suspend fun getSeries(): MovieList = movieDao.getAllMovies().filter { movie -> movie.type == AppConstants.SERIES_TYPE }.toMovieList()

    suspend fun saveMovie(movieEntity: MovieEntity){
        movieDao.saveMovie(movieEntity)
    }

}