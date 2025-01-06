package com.ferraz.playshow.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes

@Serializable
data object Splash : Routes

@Serializable
data object Home : Routes

@Serializable
data object MyList : Routes

@Serializable
data class MovieDetails(val id: Int) : Routes
