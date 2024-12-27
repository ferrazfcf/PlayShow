package com.ferraz.playshow.domain.movies.usecase.mylist.get

import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory(binds = [IsMovieOnMyList::class])
class IsMovieOnMyListImpl(
    private val repository: MovieRepository,
    private val dispatchers: DispatchersProvider
) : IsMovieOnMyList {

    override suspend fun invoke(movieId: Int): Result<Unit> {
        return withContext(dispatchers.default) {
            val movie = repository.getMovieById(movieId)
            return@withContext runCatching {
                if (movie == null) throw Exception("Movie not found")
            }
        }
    }
}
