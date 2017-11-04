package com.mobile.app.data.models

data class PopularMoviesModel(
        val id: Int,
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