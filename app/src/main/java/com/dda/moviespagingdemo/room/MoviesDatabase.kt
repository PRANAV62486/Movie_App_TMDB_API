package com.dda.moviespagingdemo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dda.moviespagingdemo.models.RemoteKeyModel

import com.dda.moviespagingdemo.models.MoviesRemoteResponse.Movie

@Database(entities = [Movie::class,RemoteKeyModel::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}