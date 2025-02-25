package com.ferraz.playshow.domain.movies.usecase.details

import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.domain.movies.mapper.MovieMapper
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory(binds = [MovieDetails::class])
class GetMovieDetails(
    private val repository: MovieRepository,
    private val dispatchers: DispatchersProvider
) : MovieDetails {
    override suspend fun invoke(movieId: Int): Result<Movie> {
        return withContext(dispatchers.default) {
            return@withContext repository.runCatching {
                fetchMovieById(movieId).fold(
                    onSuccess = ::handleSuccess,
                    onFailure = { error -> throw error }
                )
            }
        }
    }

    private fun handleSuccess(movieResponse: MovieResponse): Movie {
        val movie = MovieMapper.fromResponse(movieResponse)
        return requireNotNull(movie) { "Movie cannot be null" }
    }
}
