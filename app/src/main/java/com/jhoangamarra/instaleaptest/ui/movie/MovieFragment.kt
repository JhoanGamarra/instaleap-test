package com.jhoangamarra.instaleaptest.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhoangamarra.instaleaptest.R
import com.jhoangamarra.instaleaptest.core.Resource
import com.jhoangamarra.instaleaptest.data.local.AppDatabase
import com.jhoangamarra.instaleaptest.data.local.LocalMovieDataSource
import com.jhoangamarra.instaleaptest.data.remote.RemoteMovieDataSource
import com.jhoangamarra.instaleaptest.databinding.FragmentMovieBinding
import com.jhoangamarra.instaleaptest.domain.MovieRepositoryImpl
import com.jhoangamarra.instaleaptest.domain.RetrofitClient
import com.jhoangamarra.instaleaptest.presentation.movie.MovieViewModel
import com.jhoangamarra.instaleaptest.presentation.MovieViewModelFactory
import com.jhoangamarra.instaleaptest.ui.MovieAdapter

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var movieAdapter: MovieAdapter
    private val movieViewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.apiService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter()
        FragmentMovieBinding.bind(view).apply {
            rvMovies.layoutManager = LinearLayoutManager(this@MovieFragment.context)
            rvMovies.adapter = movieAdapter

            movieViewModel.fetchMovies().observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        movieAdapter.submitList(result.data.results)
                    }
                    is Resource.Failure -> {
                        progressBar.visibility = View.GONE
                        Log.d("Error", "${result.exception}")
                    }
                }
            })
        }

        movieAdapter.onItemClickListener = { movie ->
            Toast.makeText(context, movie.name, Toast.LENGTH_SHORT).show()
        }

    }
}