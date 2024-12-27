package com.ferraz.playshow.data.repositories

import com.ferraz.playshow.core.dispatchers.PlayShowDispatchers
import com.ferraz.playshow.data.local.dao.MovieDao
import com.ferraz.playshow.data.local.model.MovieEntity
import com.ferraz.playshow.data.remote.model.movies.MovieResponse
import com.ferraz.playshow.data.remote.service.MoviesService
import com.ferraz.playshow.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [MovieRepository::class])
class MovieRepositoryImpl(
    private val service: MoviesService,
    private val dao: MovieDao,
    private val dispatchers: PlayShowDispatchers
) : MovieRepository {

    override suspend fun fetchMovieById(movieId: Int): Result<MovieResponse> {
        return withContext(dispatchers.io) {
            service.fetchMovieById(movieId)
        }
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return dao.getAllMovies().flowOn(dispatchers.io)
    }

    override suspend fun getMovieById(movieId: Int): MovieEntity? {
        return withContext(dispatchers.io) {
            dao.getMovieById(movieId)
        }
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        withContext(dispatchers.io) {
            dao.insertMovie(movie)
        }
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        withContext(dispatchers.io) {
            dao.deleteMovie(movie)
        }
    }
}
