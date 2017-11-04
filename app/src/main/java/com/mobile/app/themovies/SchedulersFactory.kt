package com.mobile.app.themovies

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersFactory: ISchedulersFactory {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler= Schedulers.computation()

    override fun trampoline(): Scheduler = Schedulers.trampoline()
}