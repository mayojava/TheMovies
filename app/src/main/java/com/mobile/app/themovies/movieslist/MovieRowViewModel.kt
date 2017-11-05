package com.mobile.app.themovies.movieslist

data class MovieRowViewModel(
        val id: Long,
        val title: String,
        val poster_path: String,
        val backdrop_path: String,
        val release_date: String,
        val vote_count: Int,
        val overview: String
)