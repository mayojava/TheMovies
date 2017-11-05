package com.mobile.app.themovies.movieslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.mobile.app.themovies.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MoviesActivity: AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var recyclerAdapter: MoviesAdapter

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MoviesActivityViewModel::class.java) }
    private val observer: Observer<List<MovieRowViewModel>> = createObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupToolbar()
        setupRecyclerView()

        viewModel.getMoviesLiveData().observe(this, observer)
    }

    private fun createObserver(): Observer<List<MovieRowViewModel>> {
        return Observer {
            it -> kotlin.run {
                if (it == null || it.isEmpty()) {
                    loading_progress.visibility = View.VISIBLE
                } else {
                    loading_progress.visibility = View.GONE
                    recyclerAdapter.setItems(it)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recycler_view_movies.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycler_view_movies.adapter = recyclerAdapter
        recyclerAdapter.setItemClickListener { Toast.makeText(baseContext, "${it.title}", Toast.LENGTH_LONG).show() }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.tool_bar_title_movies)
    }
}