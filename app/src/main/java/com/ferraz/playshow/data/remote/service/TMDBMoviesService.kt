package com.ferraz.playshow.data.remote.service

import com.ferraz.playshow.data.remote.client.KtorClient
import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.data.remote.model.movies.MoviesResponse
import org.koin.core.annotation.Single

@Single(binds = [MoviesService::class])
class TMDBMoviesService(private val client: KtorClient) : MoviesService {

    override suspend fun fetchMovies(page: Int): Result<MoviesResponse> {
        val path = "discover/movie?include_adult=false&include_video=false&language=en-US&page=$page"
        return client.get(path)
    }

    override suspend fun fetchMovieById(movieId: Int): Result<MovieResponse> {
        val path = "movie/$movieId?language=en-US"
        return client.get(path)
    }
}
