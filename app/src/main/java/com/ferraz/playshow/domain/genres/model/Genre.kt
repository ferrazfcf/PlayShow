package com.ferraz.playshow.domain.genres.model

import com.ferraz.playshow.data.remote.model.genres.GenreResponse

data class Genre(
    val id: Int,
    val name: String
) {
    companion object {
        fun fromResponse(response: GenreResponse?): Genre? {
            return response?.runCatching {
                Genre(
                    id = requireNotNull(id) { "Genre ID cannot be null" },
                    name = requireNotNull(name) { "Genre name cannot be null" }
                )
            }?.getOrNull()
        }
    }
}
