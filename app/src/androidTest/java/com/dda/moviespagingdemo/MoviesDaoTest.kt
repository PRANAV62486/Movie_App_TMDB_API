package com.dda.moviespagingdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dda.moviespagingdemo.models.MoviesRemoteResponse
import com.dda.moviespagingdemo.room.MoviesDao
import com.dda.moviespagingdemo.room.MoviesDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDatabase: MoviesDatabase
    private lateinit var movieDao: MoviesDao

    @Before
    fun setUp() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = movieDatabase.movieDao()

    }

    @Test
    fun insertMovie_expectedSingleMovie() = runBlocking {
        val movie = MoviesRemoteResponse.Movie(movieId = 1)
        movieDao.insertMovie(movie)

        val result = movieDao.getMoviesList().getOrAwaitValue()

        Assert.assertEquals(1, result.size)
        Assert.assertEquals(1, result[0].movieId)

    }

    @Test
    fun deleteAllMovies() = runBlocking {
        val movie = MoviesRemoteResponse.Movie(movieId = 1)
        movieDao.insertMovie(movie)
        movieDao.clearMovies()

        val result = movieDao.getMoviesList().getOrAwaitValue()
        Assert.assertEquals(0, result.size)

    }

    @Test
    fun add_thousand_movies() = runBlocking {

        val movies = mutableListOf<MoviesRemoteResponse.Movie>()
        for (i in 1..10000) {
            movies.add(MoviesRemoteResponse.Movie(movieId = i))
        }

        movieDao.insertMovies(movies)

        val result = movieDao.getMoviesList().getOrAwaitValue()
        Assert.assertEquals(1000, result.size)

    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }

}