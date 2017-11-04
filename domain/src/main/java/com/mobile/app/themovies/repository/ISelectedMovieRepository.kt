package com.mobile.app.themovies.repository

import com.mobile.app.themovies.entity.Movie

interface ISelectedMovieRepository {
    fun putSelectedMovie(selectedMovie: Movie)
    fun getSelectedMovie(): Movie
}