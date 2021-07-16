package com.jhoangamarra.instaleaptest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name: String = "",
    val type: String = ""
) : Parcelable

data class MovieList(val results: List<Movie> = listOf())

fun List<MovieEntity>.toMovieList(): MovieList {
    val resultList = mutableListOf<Movie>()
    this.forEach { movieEntity ->
        resultList.add(movieEntity.toMovie())
    }
    return MovieList(resultList)
}


fun MovieEntity.toMovie(): Movie = Movie(
    this.name,
    this.type
)

fun Movie.toMovieEntity(): MovieEntity = MovieEntity(
    this.name,
    this.type,
)
