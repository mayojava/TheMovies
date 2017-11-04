package com.mobile.app.themovies

import android.app.Activity
import android.app.Application
import com.mobile.app.themovies.injection.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MoviesApplication: Application(), HasActivityInjector {

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    @Inject lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>
}