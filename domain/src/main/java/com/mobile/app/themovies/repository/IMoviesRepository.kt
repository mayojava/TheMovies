package com.mobile.app.themovies.repository

import com.mobile.app.themovies.entity.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IMoviesRepository {
    fun fetchMovies(): Completable
    fun getMoviesList(): Flowable<List<Movie>>
    fun fetchMoviesPaginated(page: Int): Single<List<Movie>>
}