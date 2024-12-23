package com.ferraz.playshow.presentation.moviedetails

import com.ferraz.playshow.domain.movies.model.Movie

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String? = null
)
