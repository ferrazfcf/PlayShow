package com.ferraz.playshow.presentation.moviedetails.model

sealed interface MovieDetailsAction {
    data object LoadDetails: MovieDetailsAction
    data object NavigateBack: MovieDetailsAction
}