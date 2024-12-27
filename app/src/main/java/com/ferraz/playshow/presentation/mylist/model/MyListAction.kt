package com.ferraz.playshow.presentation.mylist.model

import com.ferraz.playshow.presentation.navigation.MovieDetails

interface MyListAction {
    @JvmInline
    value class OpenMovieDetails(private val movieId: Int) : MyListAction {
        val route: MovieDetails
            get() = MovieDetails(movieId)
    }
}
