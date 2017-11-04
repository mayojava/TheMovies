package com.mobile.app.data.db.store

import com.mobile.app.data.db.dao.MoviesDao
import com.mobile.app.data.db.entities.MoviesEntity
import com.mobile.app.data.store.ReactiveStore
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

class MoviesReactiveStore @Inject constructor(private val moviesDao: MoviesDao): ReactiveStore<MoviesEntity> {

    override fun storeAll(objects: List<MoviesEntity>) {
        moviesDao.insertAll(objects)
    }

    override fun replaceAll(objects: List<MoviesEntity>) {
        moviesDao.deleteAll()
        storeAll(objects)
    }

    override fun getAll(): Flowable<List<MoviesEntity>> {
        return moviesDao.getAllMovies()
                .map { movies ->
                    if (movies.isEmpty()) Collections.emptyList()
                    else movies
                }
    }
}