package com.ferraz.playshow.domain.movies.model

import com.ferraz.playshow.data.remote.model.movies.MovieItemResponse

data class MovieItem(
    val id: Int,
    val title: String,
    val posterPath: String
) {

    companion object {
        fun fromResponse(response: MovieItemResponse?): MovieItem? {
            return response?.runCatching {
                val posterPath = requireNotNull(posterPath) { "Movie poster path cannot be null" }
                MovieItem(
                    id = requireNotNull(id) { "Movie ID cannot be null" },
                    title = requireNotNull(title) { "Movie title cannot be null" },
                    posterPath = "https://image.tmdb.org/t/p/w500$posterPath"
                )
            }?.getOrNull()
        }
    }
}
