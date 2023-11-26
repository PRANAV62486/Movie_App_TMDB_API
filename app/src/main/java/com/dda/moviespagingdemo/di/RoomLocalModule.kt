package com.dda.moviespagingdemo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dda.moviespagingdemo.room.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDBModule {

    @Provides
    @Singleton
    fun provideRoomDB(
        applicationContext: Application
    ): MoviesDatabase {
        return Room.databaseBuilder(
            (applicationContext as Context),
            MoviesDatabase::class.java, "movie-db"
        ).build()
    }

}