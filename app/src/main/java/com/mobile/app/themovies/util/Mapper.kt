package com.mobile.app.themovies.util

import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.movieslist.MovieRowViewModel
import javax.inject.Inject

class Mapper @Inject constructor(){

    fun toRowListItem(movies: List<Movie>): List<MovieRowViewModel> {
        val list = mutableListOf<MovieRowViewModel>()
        movies.forEach {
            list.add(MovieRowViewModel(it.id, it.title, it.poster_path ?: "", it.backdrop_path ?: "", it.release_date, it.vote_count, it.overview))
        }
        return list
    }
}