package com.mobile.app.themovies.movieslist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mobile.app.themovies.domain.interactor.GetMoreMoviesUseCase
import com.mobile.app.themovies.domain.interactor.GetMoviesUseCase
import com.mobile.app.themovies.domain.interactor.RefreshMovieDataUseCase
import com.mobile.app.themovies.util.Mapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesActivityViewModelTest {
    @Mock lateinit var getMoviesUseCase: GetMoviesUseCase
    @Mock lateinit var refreshMovieDataUseCase: RefreshMovieDataUseCase
    @Mock lateinit var getMoreMoviesUseCase: GetMoreMoviesUseCase
    @Mock lateinit var mapper: Mapper

    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesActivityViewModel

    @Before
    fun setup() {
        viewModel = MoviesActivityViewModel(getMoviesUseCase, refreshMovieDataUseCase,
                getMoreMoviesUseCase, mapper)
    }

    @Test
    fun verifyDataRefreshesOnCreate() {
        verify(refreshMovieDataUseCase).execute(any(), any(), eq(null))
    }

    @Test
    fun verifyViewModelBindsToStream() {
        verify(getMoviesUseCase).execute(any(), any(), eq(null))
    }

    @Test
    fun testLoadMoreViewCallsUseCase() {
        viewModel.loadMoreMovies()

        verify(getMoreMoviesUseCase).execute(any(), any(), eq(2))
    }

}