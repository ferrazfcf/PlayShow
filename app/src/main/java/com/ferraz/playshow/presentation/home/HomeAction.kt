package com.ferraz.playshow.presentation.home

import com.ferraz.playshow.presentation.navigation.MovieDetails

sealed interface HomeAction {
    @JvmInline
    value class OpenMovieDetails(val movieId: Int) : HomeAction {
        val route: MovieDetails
            get() = MovieDetails(movieId)
    }
    data object LoadMoreMovies : HomeAction
    data object LoadMovies : HomeAction
}