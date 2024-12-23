package com.ferraz.playshow.domain.movies.usecase.list

import com.ferraz.playshow.domain.movies.model.MovieItem

interface MoviesList {
    suspend operator fun invoke(page: Int): Result<List<MovieItem>>
}
