package com.mobile.app.data.mappers

import com.mobile.app.data.db.entities.MovieDetailsEntity
import com.mobile.app.data.db.entities.MovieEntity
import com.mobile.app.data.models.MoviesDetailsModel
import com.mobile.app.data.models.PopularMoviesModel
import com.mobile.app.themovies.domain.entity.Genres
import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.domain.entity.MovieDetails
import javax.inject.Inject

class Mapper @Inject constructor(){

    fun toMovieDetailsEntity(model: MoviesDetailsModel): MovieDetailsEntity {
        return MovieDetailsEntity(
                model.id, model.adult, model.backdrop_path,
                model.budget, getGenresAsStrings(model.genres), model.homepage, model.imdb_id, model.original_title,
                model.overview, model.poster_path, model.tagline, model.title,
                model.revenue, model.release_date, model.runtime, model.status,
                model.vote_average, model.vote_count, model.popularity
        )
    }

    fun toMovieDetails(entity: MovieDetailsEntity): MovieDetails {
        return MovieDetails(entity.adult, entity.backdrop_path, entity.budget, entity.genres,
                entity.homepage, entity.id, entity.imdb_id, entity.original_title, entity.overview,
                entity.poster_path, entity.tagline, entity.title, entity.revenue, entity.released_date,
                entity.runtime, entity.status, entity.vote_average, entity.vote_count, entity.popularity)
    }

    fun toMovieEntity(movie: PopularMoviesModel): MovieEntity {
        return MovieEntity(
                movie.id, movie.vote_count,
                movie.video, movie.title,
                movie.popularity, movie.poster_path,
                movie.original_title, movie.backdrop_path,
                movie.overview, movie.release_date, movie.adult
        )
    }

    fun toMovie(movie: PopularMoviesModel): Movie {
        return Movie(movie.id, movie.vote_count, movie.video, movie.title, movie.popularity,
                movie.poster_path, movie.original_title, movie.backdrop_path,
                movie.overview, movie.release_date, movie.adult
        )
    }

    fun toMovieList(entities: List<MovieEntity>): List<Movie> {
        val res = mutableListOf<Movie>()
        entities.forEach {
            res.add(Movie(it.id, it.vote_count, it.video, it.title, it.popularity, it.poster_path, it.original_title, it.backdrop_path, it.overview, it.release_date, it.adult))
        }
        return res
    }

    private fun getGenresAsStrings(genres: List<Genres>): String {
        val strings = StringBuilder()
        (0 until genres.size)
                .forEach {
                    if (it == genres.size-1) {
                        strings.append(genres[it].name)
                    } else {
                        strings.append("${genres[it].name},")
                    }
                }
        return strings.toString()
    }
}