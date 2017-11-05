package com.mobile.app.themovies.movieslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.app.themovies.R
import java.util.*
import javax.inject.Inject

class MoviesAdapter @Inject constructor(): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var itemClickListener: (MovieRowViewModel) -> Unit = {}
    private var items: MutableList<MovieRowViewModel> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemm = items[position]
        holder.bind(itemm, itemClickListener)
    }

    fun setItemClickListener(listener: (MovieRowViewModel) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    fun setItems(items: List<MovieRowViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(rowViewModel: MovieRowViewModel, listener: (MovieRowViewModel) -> Unit) {
            itemView.setOnClickListener { listener(rowViewModel) }
        }
    }
}