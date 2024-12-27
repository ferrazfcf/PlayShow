package com.ferraz.playshow.data.remote.service

import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.data.remote.model.movies.MoviesResponse

interface MoviesService {
    suspend fun fetchMovies(page: Int): Result<MoviesResponse>
    suspend fun fetchMovieById(movieId: Int): Result<MovieResponse>
}
