package com.mobile.app.data.db.store

import com.mobile.app.data.db.dao.MoviesDao
import com.mobile.app.data.db.entities.MovieEntity
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MoviesReactiveStoreTest {
    @Mock lateinit var moviesDao: MoviesDao

    private lateinit var reactiveStore: MoviesReactiveStore

    @Before
    fun setup() {
        reactiveStore = MoviesReactiveStore(moviesDao)
    }

    @Test
    fun storeAll_shouldCallDao() {
        //Arrange
        val list = listOf(createMovieEntity())

        //Act
        reactiveStore.storeAll(list)

        //Assert
        verify(moviesDao).insertAll(list)
    }

    @Test
    fun testReplaceAll() {
        //Arrange
        val list = listOf(createMovieEntity())

        //Act
        reactiveStore.replaceAll(list)

        //Assert
        val inoder = inOrder(moviesDao)
        inoder.verify(moviesDao).deleteAll()
        inoder.verify(moviesDao).insertAll(list)
    }

    @Test
    fun getAll_shouldReturnEmpty_whenNoRecordInDb() {
        //Arrange
        whenever(moviesDao.getAllMovies()).thenReturn(Flowable.just(Collections.emptyList()))

        //Act
        val subscriber: TestSubscriber<List<MovieEntity>> = reactiveStore.getAll().test()

        subscriber.assertNoErrors()
        subscriber.assertResult(Collections.emptyList())
    }

    @Test
    fun getAll_shouldReturnRecordsInDb_whenNotEmpty() {
        //Arrange
        val list = listOf(createMovieEntity())
        whenever(moviesDao.getAllMovies()).thenReturn(Flowable.just(list))

        //Act
        val subscriber: TestSubscriber<List<MovieEntity>> = reactiveStore.getAll().test()

        //Assert
        subscriber.assertNoErrors()
        subscriber.assertResult(list)
    }

    private fun createMovieEntity(): MovieEntity {
        return MovieEntity(2L, 10, true, "Thor", 5.6F,
                "", "Thor", "", "Thor",
                "2017-09-09", false)
    }
}