package com.mobile.app.themovies.domain.repository

import com.mobile.app.themovies.domain.entity.Movie

interface ISelectedMovieRepository {
    fun putSelectedMovie(selectedMovie: Movie)
    fun getSelectedMovie(): Movie
}