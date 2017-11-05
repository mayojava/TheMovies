package com.mobile.app.themovies.movieslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.mobile.app.themovies.domain.interactor.GetMoreMoviesUseCase
import com.mobile.app.themovies.domain.interactor.GetMoviesUseCase
import com.mobile.app.themovies.domain.interactor.RefreshMovieDataUseCase
import com.mobile.app.themovies.domain.repository.IMoviesRepository
import com.mobile.app.themovies.util.Mapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesActivityViewModel @Inject constructor(
        private val getMoviesUseCase: GetMoviesUseCase,
        private val refreshMovieDataUseCase: RefreshMovieDataUseCase,
        private val getMoreMoviesUseCase: GetMoreMoviesUseCase,
        private val mapper: Mapper): ViewModel() {

    private val liveData = MutableLiveData<List<MovieRowViewModel>>()
    private val moreMoviesLiveData = MutableLiveData<List<MovieRowViewModel>>()
    private var page = 1

    private val TAG = "MoviesViewModel"

    init {
        bindToStream()
        refreshData()
    }

    fun getMoviesLiveData(): LiveData<List<MovieRowViewModel>> = liveData

    fun getMoreMoviesLiveData(): LiveData<List<MovieRowViewModel>> = moreMoviesLiveData

    private fun refreshData()  {
         refreshMovieDataUseCase.execute({}, {error -> Log.e(TAG, error.message)}, null)
    }

    private fun bindToStream(){
        getMoviesUseCase.execute({liveData.postValue(mapper.toRowListItem(it))}, { error -> Log.e(TAG, error.message)}, null)
    }

    fun loadMoreMovies() {
        getMoreMoviesUseCase.execute({moreMoviesLiveData.postValue(mapper.toRowListItem(it))}, { error -> Log.e(TAG, error.message)}, ++page)
    }
}