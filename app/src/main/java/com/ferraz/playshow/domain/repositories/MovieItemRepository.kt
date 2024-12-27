package com.ferraz.playshow.domain.repositories

import com.ferraz.playshow.data.remote.model.movies.MoviesResponse

interface MovieItemRepository {
    suspend fun fetchMovies(page: Int): Result<MoviesResponse>
}
