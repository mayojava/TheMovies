package com.mobile.app.data.repository

import com.mobile.app.data.MoviesService
import com.mobile.app.data.db.dao.MovieDetailsDao
import com.mobile.app.data.db.entities.MovieDetailsEntity
import com.mobile.app.data.db.entities.MovieEntity
import com.mobile.app.data.mappers.Mapper
import com.mobile.app.data.models.ApiResponse
import com.mobile.app.data.models.MoviesDetailsModel
import com.mobile.app.data.models.PopularMoviesModel
import com.mobile.app.data.store.ReactiveStore
import com.mobile.app.themovies.domain.ISchedulersFactory
import com.mobile.app.themovies.domain.entity.MovieDetails
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {
    @Mock lateinit var movieService: MoviesService
    @Mock lateinit var schedulersFactory: ISchedulersFactory
    @Mock lateinit var movieStore: ReactiveStore<MovieEntity>
    @Mock lateinit var detailsDao: MovieDetailsDao

    private var mapper: Mapper = Mapper()
    private lateinit var moviesRepository: MoviesRepository
    private val testScheduler = TestScheduler()
    private val ioScheduler = TestScheduler()

    @Before
    fun setup() {
        moviesRepository = MoviesRepository(movieService, schedulersFactory, movieStore, detailsDao, mapper)
        whenever(schedulersFactory.computation()).thenReturn(testScheduler)
        whenever(schedulersFactory.io()).thenReturn(ioScheduler)
    }

    @Test
    fun getMovieDetailsShouldGetFromDbWhenRecordExists() {
        //Arrange
        val id = 3L
        whenever(detailsDao.getMovieDetails(id)).thenReturn(Single.just(createMovieDetails()))

        val observer: TestObserver<MovieDetails> = moviesRepository.getMovieDetails(id).test()

        observer.assertNoErrors()

        verify(detailsDao).getMovieDetails(id)
        verify(movieService, never()).getMoveDetails(id)
        verify(detailsDao, never()).saveMovieDetails(any())

    }

    @Test
    fun shouldGetMovieDetailsFromApiWhenNotFoundInDb() {
        val id = 3L
        whenever(detailsDao.getMovieDetails(id)).thenReturn(Single.error(NoSuchElementException()))
        whenever(movieService.getMoveDetails(id)).thenReturn(Single.just(createMovieDetailsModel()))

        val observer: TestObserver<MovieDetails> = moviesRepository.getMovieDetails(id).test()
        testScheduler.triggerActions()
        ioScheduler.triggerActions()

        observer.awaitTerminalEvent()
        observer.assertNoErrors()

        verify(detailsDao).getMovieDetails(id)
        verify(movieService).getMoveDetails(id)
        verify(detailsDao).saveMovieDetails(any())
    }

    @Test
    fun testGetMoviesListGetsFromStore() {
        //Arrange
        val list = listOf(createMovieEntity())
        whenever(movieStore.getAll()).thenReturn(Flowable.just(list))

        moviesRepository.getMoviesList()

        //Assert
        verify(movieStore).getAll()
    }

    @Test
    fun testFetchMovies() {
        //Arrange
        val page = 1
        whenever(movieService.getMovies(page)).thenReturn(Single.just(createApiResponse()))

        val observer: TestObserver<Void> = moviesRepository.fetchMovies().test()

        ioScheduler.triggerActions()
        testScheduler.triggerActions()

        observer.awaitTerminalEvent()
        observer.assertComplete()
        observer.assertNoErrors()

        //Assert
        verify(movieStore).replaceAll(any())
    }

    private fun createApiResponse(): ApiResponse {
        val popularMovie = PopularMoviesModel(10L, 10, true,
                "Thor", 3.5F, "", "Thor", "",
                "Thor", "", false)
        return ApiResponse(1, 20, 100, listOf(popularMovie))
    }

    private fun createMovieDetailsModel(): MoviesDetailsModel {
        return MoviesDetailsModel(false, "", 120L, Collections.emptyList(),
                "", 100L, "", "Thor", "Thor", "",
                "", "Thor", 1200L, "2017-10-10", 10,
                "", 10.3F, 20, 2.5F)
    }

    private fun createMovieDetails(): MovieDetailsEntity {
        return MovieDetailsEntity(10L, false, "", 10L, "Sci-Fi",
                "", "", "Iron Man", "Iron Man", "",
                "", "Iron Man", 100L,
                "2017-10-09", 10, "", 4.5F, 10, 10.0F)
    }

    private fun createMovieEntity(): MovieEntity {
        return MovieEntity(10L, 10, true, "Thor", 4.5F,
                "", "Thor", "", "Thor",
                "2017-10-03", false)
    }
}