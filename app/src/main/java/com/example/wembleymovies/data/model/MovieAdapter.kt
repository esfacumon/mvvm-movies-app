package com.example.wembleymovies.data.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymovies.R
import com.squareup.picasso.Picasso
import com.example.wembleymovies.data.api.ApiConstants.POSTER_BASE_URL
import com.example.wembleymovies.data.api.ApiConstants.POSTER_SIZE

class MovieAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieOverview: TextView = view.findViewById(R.id.movie_overview)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false)
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = movies[position]
        viewHolder.movieTitle.text = movie.title.plus("(" + movie.releaseDate.subSequence(0, 4) + ")")
        viewHolder.movieOverview.text = if(movie.overview.length >= 62) movie.overview.take(59) + "..." else movie.overview
        Log.d("imagen", POSTER_BASE_URL + POSTER_SIZE + movie.posterPath)
        Picasso.get()
            .load(POSTER_BASE_URL + POSTER_SIZE + movie.posterPath)
            .placeholder(R.drawable.baseline_image_192) // placeholder while poster loads
            .error(R.drawable.baseline_image_192) // maybe set "poster not found" image if response is error
            .into(viewHolder.moviePoster)
    }


    override fun getItemCount() = movies.size

}