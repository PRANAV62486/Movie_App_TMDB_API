package com.dda.moviespagingdemo.retrofit

import com.dda.moviespagingdemo.models.MoviesRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesRetrofitAPI {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesRemoteResponse

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesRemoteResponse


}