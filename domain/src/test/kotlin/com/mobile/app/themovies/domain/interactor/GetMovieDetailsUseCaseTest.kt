package com.mobile.app.themovies.domain.interactor

import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.MovieDetails
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseTest {

    @Mock lateinit var moviesRepository: IMoviesRepository
    @Mock lateinit var schedulersFactory: ISchedulersFactory

    private lateinit var getDetailsUseCase: GetMovieDetailsUseCase
    private val testScheduler = TestScheduler()
    private val id = 1L

    @Before
    fun setup() {
        getDetailsUseCase = GetMovieDetailsUseCase(moviesRepository, schedulersFactory)
        whenever(schedulersFactory.computation()).thenReturn(testScheduler)
        whenever(schedulersFactory.main()).thenReturn(testScheduler)
        whenever(moviesRepository.getMovieDetails(id)).thenReturn(Single.just(createMovieDetails()))
    }

    @Test
    fun shouldGetDetailsDFromRepository() {
        //Arrange

        //Act
        getDetailsUseCase.build(id)

        //Assert
        verify(moviesRepository).getMovieDetails(id)
        verifyNoMoreInteractions(moviesRepository)
    }

    private fun createMovieDetails(): MovieDetails {
        return MovieDetails(false, "", 120L,
                "Fiction", "", 100L, "", "Thor",
                "Thor Ragnarok", "", "", "Thor", 120L,
                "", 10, "", 6.5F, 2, 1.4F)
    }
}