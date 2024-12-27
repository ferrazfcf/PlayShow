package com.ferraz.playshow.domain.movies.usecase.mylist.remove

import com.ferraz.playshow.domain.movies.model.Movie

interface RemoveFromMyList {
    suspend operator fun invoke(movie: Movie): Result<Unit>
}
