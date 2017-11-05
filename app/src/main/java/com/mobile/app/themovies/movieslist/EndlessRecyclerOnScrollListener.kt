package com.mobile.app.themovies.movieslist

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerOnScrollListener: RecyclerView.OnScrollListener() {
    private var previousTotalItems = 0
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val firstVisibleItem = IntArray(3)
        (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(firstVisibleItem)

        if (loading) {
            if (totalItemCount > previousTotalItems) {
                loading = false
                previousTotalItems = totalItemCount
            }
        }

        val visibleThreshold = 0
        if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem[0] + visibleThreshold) {
            onLoadMore()
            loading = true
        }
    }

    abstract fun onLoadMore()
}