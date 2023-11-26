package com.dda.moviespagingdemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.dda.moviespagingdemo.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MoviesViewModel @Inject constructor(repository: MoviesRepository) : ViewModel() {
    val list = repository.getMovies().cachedIn(viewModelScope)
}