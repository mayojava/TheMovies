package com.mobile.app.themovies.domain.interactor

import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class RefreshMovieDataUseCase @Inject constructor(private val moviesRepository: IMoviesRepository,
                              private val schedulersFactory: ISchedulersFactory): UseCase.RxCompletable<Void>() {

    override fun build(params: Void?): Completable = moviesRepository.fetchMovies().subscribeOn(schedulersFactory.io())
}