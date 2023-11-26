package com.dda.moviespagingdemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dda.moviespagingdemo.R
import com.dda.moviespagingdemo.databinding.FragmentMovieDetailsBinding
import com.dda.moviespagingdemo.models.MoviesRemoteResponse.Movie
import com.dda.moviespagingdemo.utils.IMAGE_BASE_URL
import com.dda.moviespagingdemo.utils.IMAGE_SIZE
import com.dda.moviespagingdemo.utils.ImageMapper
import java.io.Serializable

class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMovieDetailsBinding.inflate(inflater, container, false)

        bindViews(arguments?.get("Movie") as Movie)

        return binding.root
    }

    private fun bindViews(movie: Movie){
        try {
            Glide.with(requireContext())
                .load(ImageMapper(imagePath = movie.posterPath?:"").getFullImageUrl())
                .placeholder(R.drawable.baseline_image_24)
                .into(binding.iv)

            binding.title.text = movie.title
            binding.desc.text = getString(R.string.desc,movie.overview)
            binding.releasedOn.text = getString(R.string.released_on, movie.releaseDate)
            binding.rating.text = getString(R.string.rating, movie.voteAverage.toString())
        } catch (_: Exception) {
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Serializable) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("Movie", param1)
                }
            }
    }
}