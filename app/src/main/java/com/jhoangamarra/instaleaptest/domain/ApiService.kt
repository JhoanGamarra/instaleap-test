package com.jhoangamarra.instaleaptest.domain

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jhoangamarra.instaleaptest.data.model.MovieList
import com.jhoangamarra.instaleaptest.utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("72f66f33-9186-4b20-a9a6-2c65661bc9cf")
    suspend fun getAllMovies(): MovieList

}

object RetrofitClient {

    val apiService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ApiService::class.java)

    }

}