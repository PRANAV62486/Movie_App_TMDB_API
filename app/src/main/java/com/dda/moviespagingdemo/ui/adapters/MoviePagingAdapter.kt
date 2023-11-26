package com.dda.moviespagingdemo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dda.moviespagingdemo.R
import com.bumptech.glide.Glide
import com.dda.moviespagingdemo.databinding.ItemMovieLayoutBinding
import com.dda.moviespagingdemo.models.MoviesRemoteResponse.Movie
import com.dda.moviespagingdemo.utils.IMAGE_BASE_URL
import com.dda.moviespagingdemo.utils.IMAGE_SIZE
import com.dda.moviespagingdemo.utils.ImageMapper
import com.dda.moviespagingdemo.utils.MovieClickCallback

class MoviePagingAdapter(val cb : MovieClickCallback) :
    PagingDataAdapter<Movie, MoviePagingAdapter.MoviesViewHolder>(COMPARATOR) {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMovieLayoutBinding.bind(itemView)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.tvTitle.text = item.title

            Glide.with(holder.itemView.context)
                .load(ImageMapper(imagePath =item.posterPath?:"").getFullImageUrl())
                .placeholder(R.drawable.baseline_image_24)
                .into(holder.binding.ivThumbnail)

            holder.binding.releaseDate.text = item.releaseDate

            holder.binding.root.setOnClickListener {
                cb.onMovieClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout, parent, false)
        return MoviesViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}





















