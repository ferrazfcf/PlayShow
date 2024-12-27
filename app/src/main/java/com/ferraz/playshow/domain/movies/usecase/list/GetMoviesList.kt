package com.ferraz.playshow.domain.movies.usecase.list

import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.data.remote.model.movies.MoviesResponse
import com.ferraz.playshow.domain.movies.mapper.MovieItemMapper
import com.ferraz.playshow.domain.movies.model.MovieItem
import com.ferraz.playshow.data.remote.service.MoviesService
import com.ferraz.playshow.domain.repositories.MovieItemRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory(binds = [MoviesList::class])
class GetMoviesList(
    private val repository: MovieItemRepository,
    private val dispatchers: DispatchersProvider
) : MoviesList {

    override suspend fun invoke(page: Int): Result<List<MovieItem>> {
        return withContext(dispatchers.default) {
            return@withContext repository.runCatching {
                fetchMovies(page).fold(
                    onSuccess = ::handleSuccess,
                    onFailure = { error -> throw error }
                )
            }
        }
    }

    private fun handleSuccess(response: MoviesResponse): List<MovieItem> {
        val results = requireNotNull(response.results) { "Movies results cannot be null" }
        return results.mapNotNull { MovieItemMapper.fromResponse(it) }
    }
}
