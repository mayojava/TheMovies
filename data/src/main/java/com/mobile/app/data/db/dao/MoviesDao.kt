package com.mobile.app.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mobile.app.data.db.entities.MovieEntity
import io.reactivex.Flowable

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flowable<List<MovieEntity>>

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)
}