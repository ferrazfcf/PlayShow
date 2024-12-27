package com.ferraz.playshow.domain.movies.usecase.mylist.add

import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.domain.movies.mapper.MovieMapper
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory(binds = [AddToMyList::class])
class AddToMyListImpl(
    private val repository: MovieRepository,
    private val dispatchers: DispatchersProvider
) : AddToMyList {

    override suspend fun invoke(movie: Movie): Result<Unit> {
        return withContext(dispatchers.default) {
            return@withContext runCatching {
                val movieEntity = MovieMapper.toEntity(movie)
                repository.insertMovie(movieEntity)
            }
        }
    }
}
