package com.mobile.app.themovies.injection.modudles

import android.arch.lifecycle.ViewModelProvider
import com.mobile.app.themovies.movieslist.MoviesActivity
import com.mobile.app.themovies.movieslist.MoviesListModule
import com.mobile.app.themovies.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @Binds
    abstract fun bindViewModelfactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = arrayOf(MoviesListModule::class))
    internal abstract  fun bindMoviesActivity(): MoviesActivity
}