package com.ferraz.playshow.domain.movies.model

import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.domain.genres.model.Genre
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: LocalDate,
    val genres: List<Genre>,
    val originCountry: String
) {
    companion object {
        fun fromResponse(response: MovieResponse): Movie? {
            return response.runCatching {
                val posterPath = requireNotNull(posterPath) { "Movie poster path cannot be null" }
                val releaseDate = requireNotNull(releaseDate) { "Movie release date cannot be null" }
                val genres = genres?.mapNotNull { Genre.fromResponse(it) }?.ifEmpty { null }
                val originCountry = originCountry?.filterNotNull()?.ifEmpty { null }?.joinToString()

                Movie(
                    id = requireNotNull(id) { "Movie ID cannot be null" },
                    title = requireNotNull(title) { "Movie title cannot be null" },
                    originalTitle = requireNotNull(originalTitle) { "Original movie title cannot be null" },
                    posterPath = "https://api.themoviedb.org/3$posterPath",
                    overview = requireNotNull(overview) { "Movie overview cannot be null" },
                    releaseDate = releaseDate.toLocalDate(),
                    genres = requireNotNull(genres) { "Movie genres cannot be null" },
                    originCountry = requireNotNull(originCountry) { "Movie origin country cannot be null" }
                )
            }.getOrNull()
        }
    }
}