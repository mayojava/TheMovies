package com.mobile.app.themovies.domain.interactor

import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoreMoviesUseCase @Inject constructor(private val moviesRepository: IMoviesRepository,
                                               private val schedulersFactory: ISchedulersFactory): UseCase.RxSingle<List<Movie>, Int>() {

    override fun build(params: Int?): Single<List<Movie>> {
        return moviesRepository.fetchMoviesPaginated(params ?: 1)
                .subscribeOn(schedulersFactory.io())
                .observeOn(schedulersFactory.main())
    }
}