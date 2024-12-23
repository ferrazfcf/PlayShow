package com.ferraz.playshow.domain.remote

import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.data.remote.model.movies.MoviesResponse

interface MoviesService {
    suspend fun getMovies(page: Int): Result<MoviesResponse>
    suspend fun getMovieDetails(movieId: Int): Result<MovieResponse>
}
