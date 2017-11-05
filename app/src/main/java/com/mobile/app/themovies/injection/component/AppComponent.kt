package com.mobile.app.themovies.injection.component

import android.app.Application
import com.mobile.app.themovies.MoviesApplication
import com.mobile.app.themovies.injection.modudles.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DatabaseModule::class,
        ViewModelBuilder::class,
        BuildersModule::class,
        NetworkModule::class,
        ApiModule::class
))
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: MoviesApplication)
}