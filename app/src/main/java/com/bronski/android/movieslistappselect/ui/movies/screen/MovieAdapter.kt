package com.bronski.android.movieslistappselect.ui.movies.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bronski.android.movieslistappselect.R
import com.bronski.android.movieslistappselect.core.api.data.Movie
import com.bronski.android.movieslistappselect.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(
        val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemMovie: Movie) = with(binding) {

            titleTextView.text = itemMovie.displayTitle
            descriptionTextView.text = itemMovie.summaryShort
            dateTextView.text = itemMovie.publicationDate

            Glide.with(posterImageView.context)
                .load(itemMovie.multimedia.posterUrl)
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .error(R.drawable.ic_baseline_local_movies_24)
                .into(posterImageView)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean {
                return oldItem.displayTitle == newItem.displayTitle
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}