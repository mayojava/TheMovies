package com.mobile.app.data.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailsEntity (
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Long,
    val genres: String,
    val homepage: String,
    val imdb_id: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val tagline: String,
    val title: String,
    val revenue: Long,
    val released_date: String,
    val runtime: Int,
    val status: String,
    val vote_average: Float,
    val vote_count: Int,
    val popularity: Float
)