package com.ferraz.playshow.domain.movies.usecase.mylist.remove

import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.domain.movies.mapper.MovieMapper
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory(binds = [RemoveFromMyList::class])
class RemoveFromMyListImpl(
    private val repository: MovieRepository,
    private val dispatchers: DispatchersProvider
) : RemoveFromMyList {

    override suspend fun invoke(movie: Movie): Result<Unit> {
        return withContext(dispatchers.default) {
            return@withContext runCatching {
                val movieEntity = MovieMapper.toEntity(movie)
                repository.deleteMovie(movieEntity)
            }
        }
    }
}
