package com.mobile.app.themovies.domain.interactor

import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import io.reactivex.Flowable

class GetMoviesUseCase constructor(private val moviesRepository: IMoviesRepository,
                                   private val schedulersFactory: ISchedulersFactory): UseCase.RxFlowable<List<Movie>, Void>() {

    override fun build(params: Void?): Flowable<List<Movie>>  = moviesRepository.getMoviesList().subscribeOn(schedulersFactory.computation())
}