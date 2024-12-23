package com.ferraz.playshow.data.remote.model.movies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItemResponse(
    val id: Int?,
    val title: String?,
    @SerialName("poster_path") val posterPath: String?
)
