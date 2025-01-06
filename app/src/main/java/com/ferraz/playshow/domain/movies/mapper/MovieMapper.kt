package com.ferraz.playshow.domain.movies.mapper

import com.ferraz.playshow.data.local.model.MovieEntity
import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.domain.genres.model.Genre
import com.ferraz.playshow.domain.movies.model.Movie
import kotlinx.datetime.toLocalDate

object MovieMapper {

    fun fromResponse(response: MovieResponse): Movie? {
        return response.runCatching {
            val posterPath = requireNotNull(posterPath) { "Movie poster path cannot be null" }
            val releaseDate = requireNotNull(releaseDate) { "Movie release date cannot be null" }
            val genres = genres?.mapNotNull { Genre.fromResponse(it) }?.ifEmpty { null }?.joinToString {
                it.name
            }
            val originCountry = originCountry?.filterNotNull()?.ifEmpty { null }?.joinToString()

            Movie(
                id = requireNotNull(id) { "Movie ID cannot be null" },
                title = requireNotNull(title) { "Movie title cannot be null" },
                originalTitle = requireNotNull(originalTitle) { "Original movie title cannot be null" },
                posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
                overview = requireNotNull(overview) { "Movie overview cannot be null" },
                releaseYear = releaseDate.toLocalDate().year.toString(),
                genres = requireNotNull(genres) { "Movie genres cannot be null" },
                originCountry = requireNotNull(originCountry) { "Movie origin country cannot be null" }
            )
        }.getOrNull()
    }

    fun toEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            title = movie.title,
            originalTitle = movie.originalTitle,
            posterUrl = movie.posterUrl,
            overview = movie.overview,
            releaseYear = movie.releaseYear,
            genres = movie.genres,
            originCountry = movie.originCountry
        )
    }
}
