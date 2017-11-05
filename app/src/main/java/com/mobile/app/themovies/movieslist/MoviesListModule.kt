package com.mobile.app.themovies.movieslist

import android.arch.lifecycle.ViewModel
import com.mobile.app.themovies.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MoviesListModule {
    @Binds
    @IntoMap
    @ViewModelKey(MoviesActivityViewModel::class)
    abstract fun bindMoviesActivityViewModel(viewModel: MoviesActivityViewModel): ViewModel
}