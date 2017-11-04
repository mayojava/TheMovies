package com.mobile.app.data.models

import com.mobile.app.themovies.entity.Genres

data class MoviesDetailsModel(
        val adult: Boolean,
        val backdrop_path: String,
        val budget: Long,
        val genres: List<Genres>,
        val homepage: String,
        val id: Long,
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