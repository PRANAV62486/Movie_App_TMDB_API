package com.dda.moviespagingdemo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dda.moviespagingdemo.paging.MoviesRemoteMediator
import com.dda.moviespagingdemo.retrofit.MoviesRetrofitAPI
import com.dda.moviespagingdemo.room.MoviesDatabase
import com.dda.moviespagingdemo.utils.PAGE_SIZE_PAGING_REMOTE_MOVIE
import javax.inject.Inject

@ExperimentalPagingApi
class MoviesRepository @Inject constructor(
    private val moviesRetrofitAPI: MoviesRetrofitAPI,
    private val moviesDatabase: MoviesDatabase
) {
    fun getMovies() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE_PAGING_REMOTE_MOVIE, maxSize = 100),
        remoteMediator = MoviesRemoteMediator(moviesRetrofitAPI,moviesDatabase),
        pagingSourceFactory = { moviesDatabase.movieDao().getMovies() }
    ).liveData
}