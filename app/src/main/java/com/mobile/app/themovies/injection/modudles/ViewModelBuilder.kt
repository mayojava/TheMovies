package com.mobile.app.themovies.injection.modudles

import android.arch.lifecycle.ViewModelProvider
import com.mobile.app.themovies.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}