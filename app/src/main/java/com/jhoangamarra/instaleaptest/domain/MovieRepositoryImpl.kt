package com.jhoangamarra.instaleaptest.domain

import android.util.Log
import com.jhoangamarra.instaleaptest.core.InternetCheck
import com.jhoangamarra.instaleaptest.data.local.LocalMovieDataSource
import com.jhoangamarra.instaleaptest.data.model.MovieList
import com.jhoangamarra.instaleaptest.data.model.toMovieEntity
import com.jhoangamarra.instaleaptest.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getAllMovies(): MovieList {

        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getAllMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity())
            }
            dataSourceLocal.getAllMovies()
        } else {
            dataSourceLocal.getAllMovies()
        }
    }

    override suspend fun getMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getAllMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity())
            }
            dataSourceLocal.getMovies()
        } else {
            dataSourceLocal.getMovies()
        }
    }

    override suspend fun getSeries(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getAllMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity())
            }
            dataSourceLocal.getSeries()
        } else {
            dataSourceLocal.getSeries()
        }
    }
}