package com.ferraz.playshow.domain.movies.usecase.mylist.add

import com.ferraz.playshow.domain.movies.model.Movie

interface AddToMyList {
    suspend operator fun invoke(movie: Movie): Result<Unit>
}
