package com.dda.moviespagingdemo.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

//model of remote res and entity for local db
@Keep
data class MoviesRemoteResponse(
    @field:Json(name = "page")
    val page: Int? = 0, // 1
    @field:Json(name = "results")
    val movies: List<Movie>? = listOf(),
    @field:Json(name = "total_pages")
    val totalPages: Int? = 0, // 35413
    @field:Json(name = "total_results")
    val totalResults: Int? = 0 // 708260
) : Serializable {
    @Keep
    @Entity(tableName = "movies")
    data class Movie(
        @PrimaryKey(autoGenerate = true)
        var movieDbId:Int=0,

        @field:Json(name = "adult")
        val adult: Boolean? = false,
        @field:Json(name = "backdrop_path")
        val backdropPath: String? = "",
//        @field:Json(name = "genre_ids")
//        val genreIds: List<Int?>? = listOf(),
        @field:Json(name = "id")
        var movieId: Int = 0,
        @field:Json(name = "original_language")
        val originalLanguage: String? = "",
        @field:Json(name = "original_title")
        val originalTitle: String? = "",
        @field:Json(name = "overview")
        val overview: String? = "",
        @field:Json(name = "popularity")
        val popularity: Double? = 0.0,
        @field:Json(name = "poster_path")
        val posterPath: String? = "",
        @field:Json(name = "release_date")
        val releaseDate: String? = "",
        @field:Json(name = "title")
        val title: String? = "",
        @field:Json(name = "video")
        val video: Boolean? = false,
        @field:Json(name = "vote_average")
        val voteAverage: Double? = 0.0,
        @field:Json(name = "vote_count")
        val voteCount: Int? = 0
    ) : Serializable
}
