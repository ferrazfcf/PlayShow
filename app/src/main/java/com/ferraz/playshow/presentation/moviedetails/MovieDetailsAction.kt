package com.ferraz.playshow.presentation.moviedetails

sealed interface MovieDetailsAction {
    data object LoadDetails: MovieDetailsAction
    data object NavigateBack: MovieDetailsAction
}
