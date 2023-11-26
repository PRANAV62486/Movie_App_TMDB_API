package com.dda.moviespagingdemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val prevPage: Int?,
    val nextPage: Int?
)