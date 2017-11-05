package com.mobile.app.themovies.moviesdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mobile.app.themovies.R
import com.mobile.app.themovies.domain.entity.MovieDetails
import com.mobile.app.themovies.util.fromUrl
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_details.*
import javax.inject.Inject

class MovieDetailsActivity: AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java) }

    companion object {
        const val KEY_MOVIE_TITLE = "movie_title"
        const val KEY_MOVIE_OVERVIEW = "movie_overview"
        const val KEY_MOVIE_ID = "movie_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewModel.getDetailsLiveData().observe(this, Observer { updateMovieDetails(it) })
        val id = intent.getLongExtra(KEY_MOVIE_ID, 0L)
        viewModel.getMovieDetails(id)
        bind()
    }

    private fun bind() {
        val title = intent.getStringExtra(KEY_MOVIE_TITLE)
        val overview = intent.getStringExtra(KEY_MOVIE_OVERVIEW)

        text_view_title.text = title
        text_view_description.text = overview

        toolbar.title = title
    }

    private fun updateMovieDetails(details: MovieDetails?) {
        text_view_title.text = details?.title
        text_view_description.text = details?.overview
        text_view_movie_genre.text = details?.genres
        text_view_released_date.text = details?.released_date
        text_vote_average.text = details?.vote_average.toString()
        back_drop_image.fromUrl(details?.backdrop_path ?: "", "w780")
    }
}