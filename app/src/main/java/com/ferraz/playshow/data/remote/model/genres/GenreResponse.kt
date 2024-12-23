package com.ferraz.playshow.data.remote.model.genres

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int?,
    val name: String?
)
