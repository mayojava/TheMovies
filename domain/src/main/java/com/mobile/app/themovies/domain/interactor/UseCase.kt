package com.mobile.app.themovies.domain.interactor

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class UseCase<out Type, in Param> where Type: Any {

    internal val disposables = CompositeDisposable()

    fun dispose() = disposables.dispose()

    abstract fun build(params: Param?): Type

    abstract class RxSingle<T, in P>: UseCase<Single<T>, P>() {
        fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, params: P? = null) =
                disposables.add(build(params).subscribe(onSuccess, onError))

        fun execute(observer: UsecaseObserver.RxSingle<T>, params: P? = null) =
                disposables.add(build(params).subscribeWith(observer))
    }

    abstract class RxFlowable<T, in P>: UseCase<Flowable<T>, P>() {
        fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, params: P? = null) =
                disposables.add(build(params).subscribe(onSuccess, onError))

        fun execute(observer: UsecaseObserver.RxFlowable<T>, params: P? = null) =
                disposables.add(build(params).subscribeWith(observer))
    }

    abstract class RxCompletable<in P>: UseCase<Completable, P>() {
        fun execute(onComplete: () -> Unit = {}, params: P? = null) =
                disposables.add(build(params).subscribe(onComplete))

        fun execute(params: P? = null) = execute({}, params)

        fun execute(onComplete: () -> Unit, onError: (Throwable) -> Unit, params: P? = null) =
                disposables.add(build(params).subscribe(onComplete, onError))
    }
}