package com.mobile.app.themovies.movieslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.mobile.app.themovies.domain.interactor.GetMoviesUseCase
import com.mobile.app.themovies.domain.interactor.RefreshMovieDataUseCase
import com.mobile.app.themovies.util.Mapper
import javax.inject.Inject

class MoviesActivityViewModel @Inject constructor(
        private val getMoviesUseCase: GetMoviesUseCase,
        private val refreshMovieDataUseCase: RefreshMovieDataUseCase,
        private val mapper: Mapper): ViewModel() {

    private val liveData = MutableLiveData<List<MovieRowViewModel>>()
    private val TAG = "MoviesViewModel"

    init {
        bindToStream()
        refreshData()
    }

    private fun refreshData()  {
        refreshMovieDataUseCase.execute(null)
    }

    private fun bindToStream(){
        getMoviesUseCase.execute({liveData.postValue(mapper.toRowListItem(it))}, { error -> Log.e(TAG, error.message)}, null)
    }
}