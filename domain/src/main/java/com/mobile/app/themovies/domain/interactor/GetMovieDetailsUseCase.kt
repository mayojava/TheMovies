package com.mobile.app.themovies.domain.interactor

import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.MovieDetails
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: IMoviesRepository,
                                                 private val schedulersFactory: ISchedulersFactory): UseCase.RxSingle<MovieDetails, Long>() {

    override fun build(params: Long?): Single<MovieDetails> =
            moviesRepository.getMovieDetails(params!!)
                    .subscribeOn(schedulersFactory.computation())
                    .observeOn(schedulersFactory.main())
}