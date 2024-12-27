package com.ferraz.playshow.domain.movies.usecase.mylist.get

interface IsMovieOnMyList {
    suspend operator fun invoke(movieId: Int): Result<Unit>
}
