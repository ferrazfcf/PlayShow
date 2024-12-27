package com.ferraz.playshow.domain.repositories

import com.ferraz.playshow.data.local.model.MovieEntity
import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMovieById(movieId: Int): Result<MovieResponse>
    fun getAllMovies(): Flow<List<MovieEntity>>
    suspend fun getMovieById(movieId: Int): MovieEntity?
    suspend fun insertMovie(movie: MovieEntity)
    suspend fun deleteMovie(movie: MovieEntity)
}
