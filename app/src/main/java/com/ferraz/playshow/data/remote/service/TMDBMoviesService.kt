package com.ferraz.playshow.data.remote.service

import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.data.remote.client.KtorClient
import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.data.remote.model.movies.MoviesResponse
import com.ferraz.playshow.domain.remote.MoviesService
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [MoviesService::class])
class TMDBMoviesService(
    private val client: KtorClient,
    private val dispatchers: DispatchersProvider
) : MoviesService {

    override suspend fun getMovies(page: Int): Result<MoviesResponse> {
        val path = "discover/movie?include_adult=false&include_video=false&language=en-US&page=$page"
        return withContext(dispatchers.io) {
            client.get(path)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieResponse> {
        val path = "movie/$movieId?language=en-US"
        return withContext(dispatchers.io) {
            client.get(path)
        }
    }
}
