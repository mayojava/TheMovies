package com.mobile.app.themovies.interactor

import com.mobile.app.themovies.ISchedulersFactory
import com.mobile.app.themovies.entity.Movie
import com.mobile.app.themovies.repository.IMoviesRepository
import io.reactivex.Flowable

class GetMoviesUseCase constructor(private val moviesRepository: IMoviesRepository,
                                   private val schedulersFactory: ISchedulersFactory): UseCase.RxFlowable<List<Movie>, Void>() {

    override fun build(params: Void?): Flowable<List<Movie>>  = moviesRepository.getMoviesList().subscribeOn(schedulersFactory.computation())
}