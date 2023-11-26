package com.dda.moviespagingdemo.utils

import com.dda.moviespagingdemo.models.MoviesRemoteResponse.Movie

interface MovieClickCallback {

    fun onMovieClick(movie : Movie)
}