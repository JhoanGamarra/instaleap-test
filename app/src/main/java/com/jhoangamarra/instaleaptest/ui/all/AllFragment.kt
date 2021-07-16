package com.jhoangamarra.instaleaptest.ui.all

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
import com.jhoangamarra.instaleaptest.databinding.FragmentAllBinding
import com.jhoangamarra.instaleaptest.domain.MovieRepositoryImpl
import com.jhoangamarra.instaleaptest.domain.RetrofitClient
import com.jhoangamarra.instaleaptest.presentation.all.AllViewModel
import com.jhoangamarra.instaleaptest.presentation.MovieViewModelFactory
import com.jhoangamarra.instaleaptest.ui.MovieAdapter

class AllFragment : Fragment(R.layout.fragment_all) {

    private lateinit var movieAdapter: MovieAdapter
    private val allViewModel by viewModels<AllViewModel> {
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

        FragmentAllBinding.bind(view).apply {
            rvAllFilm.layoutManager = LinearLayoutManager(this@AllFragment.context)
            rvAllFilm.adapter = movieAdapter

            allViewModel.fetchAllMovies().observe(viewLifecycleOwner, { result ->
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
                        Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })

            movieAdapter.onItemClickListener = { movie ->
                Toast.makeText(context, movie.name, Toast.LENGTH_SHORT).show()
            }

        }
    }


}