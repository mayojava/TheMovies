package com.mobile.app.themovies.injection.modudles

import android.arch.persistence.room.Room
import android.content.Context
import com.mobile.app.data.db.MoviesDatabase
import com.mobile.app.data.db.dao.MovieDetailsDao
import com.mobile.app.data.db.dao.MoviesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesMoviesDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, "movies-database").build()
    }

    @Provides
    @Singleton
    fun providesMoviesDao(database: MoviesDatabase): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideesMoviesDetailsDao(database: MoviesDatabase): MovieDetailsDao {
        return database.moviesDetailsDao()
    }
}