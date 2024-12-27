package com.ferraz.playshow.domain.movies.mapper

import com.ferraz.playshow.data.remote.model.movies.MovieItemResponse
import com.ferraz.playshow.domain.movies.model.MovieItem

object MovieItemMapper {

    fun fromResponse(response: MovieItemResponse?): MovieItem? {
        return response?.runCatching {
            val posterPath = requireNotNull(posterPath) { "Movie poster path cannot be null" }
            MovieItem(
                id = requireNotNull(id) { "Movie ID cannot be null" },
                title = requireNotNull(title) { "Movie title cannot be null" },
                posterUrl = "https://image.tmdb.org/t/p/w500$posterPath"
            )
        }?.getOrNull()
    }
}