package com.dda.moviespagingdemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dda.moviespagingdemo.R
import com.dda.moviespagingdemo.databinding.FragmentMovieListBinding
import com.dda.moviespagingdemo.models.MoviesRemoteResponse.Movie
import com.dda.moviespagingdemo.ui.adapters.MoviePagingAdapter
import com.dda.moviespagingdemo.utils.MovieClickCallback
import com.dda.moviespagingdemo.viewmodels.MoviesViewModel

@ExperimentalPagingApi
class MovieListFragment : Fragment(), MovieClickCallback {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieViewModel: MoviesViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        recyclerView =binding.movieList

        movieViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)

        adapter = MoviePagingAdapter(this)

        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        movieViewModel.list.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()

    }

    override fun onMovieClick(movie: Movie) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer,MovieDetailsFragment.newInstance(movie))
            .addToBackStack(MovieListFragment::class.java.name)
            .commit()
    }
}