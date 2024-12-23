package com.ferraz.playshow.domain.movies.usecase.details

import com.ferraz.playshow.domain.movies.model.Movie

interface MovieDetails {
    suspend operator fun invoke(movieId: Int): Result<Movie>
}
