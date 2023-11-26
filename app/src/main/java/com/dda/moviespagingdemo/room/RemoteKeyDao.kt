package com.dda.moviespagingdemo.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dda.moviespagingdemo.models.RemoteKeyModel

@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM REMOTE_KEYS WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): RemoteKeyModel

    @Query("SELECT * FROM REMOTE_KEYS ")
    fun getAllRemoteKeys(): LiveData<List<RemoteKeyModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeyModel>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}