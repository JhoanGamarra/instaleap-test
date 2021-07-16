package com.jhoangamarra.instaleaptest.ui.series

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
import com.jhoangamarra.instaleaptest.databinding.FragmentSeriesBinding
import com.jhoangamarra.instaleaptest.domain.MovieRepositoryImpl
import com.jhoangamarra.instaleaptest.domain.RetrofitClient
import com.jhoangamarra.instaleaptest.presentation.MovieViewModelFactory
import com.jhoangamarra.instaleaptest.presentation.series.SeriesViewModel
import com.jhoangamarra.instaleaptest.ui.MovieAdapter

class SeriesFragment : Fragment(R.layout.fragment_series) {

    private lateinit var seriesAdapter: MovieAdapter
    private val seriesViewModel by viewModels<SeriesViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.apiService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAdapter = MovieAdapter()
        FragmentSeriesBinding.bind(view).apply {
            rvSeries.layoutManager = LinearLayoutManager(this@SeriesFragment.context)
            rvSeries.adapter = seriesAdapter

            seriesViewModel.fetchSeries().observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        seriesAdapter.submitList(result.data.results)
                    }
                    is Resource.Failure -> {
                        progressBar.visibility = View.GONE
                        Log.d("Error", "${result.exception}")
                    }
                }
            })

        }

        seriesAdapter.onItemClickListener = { movie ->
            Toast.makeText(context, movie.name, Toast.LENGTH_SHORT).show()
        }

    }
}