package com.ferraz.playshow.domain.movies.mapper

import com.ferraz.playshow.data.local.model.MovieEntity
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

    fun fromEntity(entity: MovieEntity): MovieItem {
        return MovieItem(
            id = entity.id,
            title = entity.title,
            posterUrl = entity.posterUrl
        )
    }
}
