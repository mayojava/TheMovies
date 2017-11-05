package com.mobile.app.themovies.injection.modudles

import com.mobile.app.themovies.injection.scope.PerActivity
import com.mobile.app.themovies.movieslist.MoviesActivity
import com.mobile.app.themovies.movieslist.MoviesListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = arrayOf(MoviesListModule::class))
    @PerActivity
    internal abstract  fun bindMoviesActivity(): MoviesActivity
}