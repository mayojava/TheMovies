package com.mobile.app.themovies.domain.interactor

import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.Movie
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesUseCaseTest {
    @Mock lateinit var moviesRepository: IMoviesRepository
    @Mock lateinit var schedulersFactory: ISchedulersFactory

    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private val testScheduler = TestScheduler()

    @Before
    fun setup() {
        getMoviesUseCase = GetMoviesUseCase(moviesRepository, schedulersFactory)
        whenever(schedulersFactory.computation()).thenReturn(testScheduler)
        whenever(schedulersFactory.main()).thenReturn(testScheduler)
    }

    @Test
    fun shouldFetchMoviesFromRepository() {
        //Arrange
        whenever(moviesRepository.getMoviesList()).thenReturn(Flowable.just(listOf(createTestMovie())))

        //Act
        getMoviesUseCase.build(null)

        //Assert
        verify(moviesRepository).getMoviesList()
        verifyNoMoreInteractions(moviesRepository)
    }

    private fun createTestMovie(): Movie {
        return Movie(1L, 10, false, "Thor", 1.3F,
                "", "Thor", "", "Thor Ragnarok",
                "", false)
    }
}