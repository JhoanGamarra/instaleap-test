package com.jhoangamarra.instaleaptest.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhoangamarra.instaleaptest.R
import com.jhoangamarra.instaleaptest.data.model.Movie
import com.jhoangamarra.instaleaptest.databinding.ItemMovieBinding
import com.jhoangamarra.instaleaptest.utils.AppConstants


class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Movie) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val film = getItem(position)
        holder.bind(film)
    }

    inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemMovieBinding.bind(view)

        fun bind(movie: Movie) {
            setIconType(movie, binding)
            binding.tvName.text = movie.name
            view.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(movie)
                } else {
                    Log.d("MovieAdapterOnClick", "onItemClickListener not initialized")
                }
            }
        }
    }
}

private fun setIconType(movie: Movie, binding: ItemMovieBinding) {

    when (movie.type) {
        AppConstants.MOVIE_TYPE -> {
            binding.ivIcon.setImageResource(R.drawable.ic_movie)
        }
        AppConstants.SERIES_TYPE -> {
            binding.ivIcon.setImageResource(R.drawable.ic_series)
        }
    }
}



