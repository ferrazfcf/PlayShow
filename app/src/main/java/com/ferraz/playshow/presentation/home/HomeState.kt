package com.ferraz.playshow.presentation.home

import com.ferraz.playshow.domain.movies.model.MovieItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    val isLoading: Boolean = false,
    val moviesList: ImmutableList<MovieItem> = emptyList<MovieItem>().toImmutableList(),
    val currentPage: Int = 1,
    val errorMessage: String? = null
)
