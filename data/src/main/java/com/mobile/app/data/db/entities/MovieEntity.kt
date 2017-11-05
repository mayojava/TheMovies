package com.mobile.app.data.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
        @PrimaryKey
        val id: Long,
        val vote_count: Int,
        val video: Boolean,
        val title: String,
        val popularity: Float,
        val poster_path: String,
        val original_title: String,
        val backdrop_path: String,
        val overview: String,
        val release_date: String,
        val adult: Boolean
)