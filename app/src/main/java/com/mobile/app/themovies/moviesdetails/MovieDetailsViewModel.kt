package com.mobile.app.themovies.moviesdetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.mobile.app.themovies.domain.entity.MovieDetails
import com.mobile.app.themovies.domain.interactor.GetMovieDetailsUseCase
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase): ViewModel() {

    private val liveData = MutableLiveData<MovieDetails>()

    fun getMovieDetails(id: Long) {
        getMovieDetailsUseCase.execute({ liveData.postValue(it) }, { error -> Log.e("MOVIE_DETAILS", error.message)}, id)
    }

    fun getDetailsLiveData(): LiveData<MovieDetails> = liveData
}