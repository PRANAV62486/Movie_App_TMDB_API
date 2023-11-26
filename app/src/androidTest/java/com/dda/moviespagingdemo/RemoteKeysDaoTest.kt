package com.dda.moviespagingdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dda.moviespagingdemo.models.MoviesRemoteResponse
import com.dda.moviespagingdemo.models.RemoteKeyModel
import com.dda.moviespagingdemo.room.MoviesDao
import com.dda.moviespagingdemo.room.MoviesDatabase
import com.dda.moviespagingdemo.room.RemoteKeyDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemoteKeysDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDatabase: MoviesDatabase
    private lateinit var keyDao: RemoteKeyDao

    @Before
    fun setUp() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).allowMainThreadQueries().build()

        keyDao = movieDatabase.remoteKeyDao()

    }

    @Test
    fun insertKey_expectedSingleKey() = runBlocking {
        val movie = mutableListOf<RemoteKeyModel>().also {
            it.add(RemoteKeyModel(2, 1, 3))
        }

        keyDao.addAllRemoteKeys(movie)

        val result = keyDao.getRemoteKeys(2)

        Assert.assertEquals(1, result.prevPage)
        Assert.assertEquals(3, result.nextPage)
    }

    @Test
    fun deleteAllKeys() = runBlocking {
        val movie = mutableListOf<RemoteKeyModel>().also {
            it.add(RemoteKeyModel(2, 1, 3))
        }

        keyDao.addAllRemoteKeys(movie)
        keyDao.deleteAllRemoteKeys()

        val result = keyDao.getAllRemoteKeys().getOrAwaitValue()
        Assert.assertEquals(0, result.size)

    }

    @Test
    fun add_thousand_movies() = runBlocking {

        val keys = mutableListOf<RemoteKeyModel>()
        for (i in 1..10000) {
            keys.add(RemoteKeyModel(i, i-1, i+1))
        }

        keyDao.addAllRemoteKeys(keys)

        val result = keyDao.getAllRemoteKeys().getOrAwaitValue()
        Assert.assertEquals(1000, result.size)

    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }

}