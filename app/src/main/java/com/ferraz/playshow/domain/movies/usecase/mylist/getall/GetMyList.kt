package com.ferraz.playshow.domain.movies.usecase.mylist.getall

import com.ferraz.playshow.core.dispatchers.PlayShowDispatchers
import com.ferraz.playshow.domain.movies.mapper.MovieItemMapper
import com.ferraz.playshow.domain.movies.model.MovieItem
import com.ferraz.playshow.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory(binds = [MyList::class])
class GetMyList(
    private val repository: MovieRepository,
    private val dispatchers: PlayShowDispatchers
) : MyList {

    override fun invoke(): Flow<List<MovieItem>> {
        return repository.getAllMovies()
            .map { myList -> myList
                .sortedByDescending { movie -> movie.releaseYear }
                .map(MovieItemMapper::fromEntity) }
            .flowOn(dispatchers.default)
    }
}
