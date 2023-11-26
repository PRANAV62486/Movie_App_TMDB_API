package com.dda.moviespagingdemo.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.dda.moviespagingdemo.models.MoviesRemoteResponse.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies order by movieDbId")
    fun getMovies() : PagingSource<Int, Movie>

    @Query("SELECT * FROM movies order by movieDbId")
    fun getMoviesList() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieLocal: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

}