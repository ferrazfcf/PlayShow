package com.ferraz.playshow.data.repositories

import com.ferraz.playshow.core.dispatchers.PlayShowDispatchers
import com.ferraz.playshow.data.remote.model.movies.MoviesResponse
import com.ferraz.playshow.data.remote.service.MoviesService
import com.ferraz.playshow.domain.repositories.MovieItemRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [MovieItemRepository::class])
class MovieItemRepositoryImpl(
    private val service: MoviesService,
    private val dispatchers: PlayShowDispatchers
) : MovieItemRepository {

    override suspend fun fetchMovies(page: Int): Result<MoviesResponse> {
        return withContext(dispatchers.io) {
            service.fetchMovies(page)
        }
    }
}
