package com.mobile.app.themovies.domain.repository

import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.domain.entity.MovieDetails
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IMoviesRepository {
    fun fetchMovies(): Completable
    fun getMoviesList(): Flowable<List<Movie>>
    fun fetchMoviesPaginated(page: Int): Single<List<Movie>>
    fun getMovieDetails(id: Int): Single<MovieDetails>
}