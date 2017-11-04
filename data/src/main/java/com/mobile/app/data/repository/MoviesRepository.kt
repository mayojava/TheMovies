package com.mobile.app.data.repository

import com.mobile.app.data.MoviesService
import com.mobile.app.data.db.dao.MovieDetailsDao
import com.mobile.app.data.db.entities.MovieEntity
import com.mobile.app.data.mappers.Mapper
import com.mobile.app.data.store.ReactiveStore
import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.domain.entity.MovieDetails
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import io.reactivex.*
import javax.inject.Inject

class MoviesRepository @Inject constructor(
        private val moviesService: MoviesService,
        private val schedulerFactory: ISchedulersFactory,
        private val movieStore: ReactiveStore<MovieEntity>,
        private val detailsDao: MovieDetailsDao,
        private val mapper: Mapper
): IMoviesRepository {

    override fun getMovieDetails(id: Int): Single<MovieDetails> {
        return detailsDao.getMovieDetails(id)
                .switchIfEmpty(Maybe.fromAction {
                    moviesService.getMoveDetails(id)
                            .map { mapper.toMovieDetailsEntity(it) }
                            .doOnSuccess { detailsDao.saveMovieDetails(it) }
                })
                .flatMapSingle { it -> Single.just(it) }
                .map { mapper.toMovieDetails(it) }
                .subscribeOn(schedulerFactory.io())
    }

    override fun fetchMovies(): Completable {
        return moviesService.getMovies(1)
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.computation())
                .flatMapObservable { Observable.fromIterable(it) }
                .map { mapper.toMovieEntity(it) }
                .toList()
                .doOnSuccess { movieStore.replaceAll(it) }
                .toCompletable()
    }

    override fun getMoviesList(): Flowable<List<Movie>> {
        return movieStore.getAll()
                .flatMapIterable { it -> it}
                .map { mapper.toMovieFromEntity(it) }
                .toList()
                .toFlowable()

    }

    override fun fetchMoviesPaginated(page: Int): Single<List<Movie>> {
        return moviesService.getMovies(page)
                .flatMapObservable { Observable.fromIterable(it) }
                .map { mapper.toMovie(it) }
                .toList()
    }
}