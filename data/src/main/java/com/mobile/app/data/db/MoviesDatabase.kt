package com.mobile.app.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mobile.app.data.db.dao.MovieDetailsDao
import com.mobile.app.data.db.dao.MoviesDao
import com.mobile.app.data.db.entities.MovieDetailsEntity
import com.mobile.app.data.db.entities.MovieEntity

@Database(entities = arrayOf(MovieEntity::class, MovieDetailsEntity::class), version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun moviesDetailsDao(): MovieDetailsDao
}