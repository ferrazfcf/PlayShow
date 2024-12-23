package com.ferraz.playshow.data.remote.model.movies

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Int?,
    val results: List<MovieItemResponse?>?,
)
