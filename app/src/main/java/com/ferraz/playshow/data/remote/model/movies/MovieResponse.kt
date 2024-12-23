package com.ferraz.playshow.data.remote.model.movies

import com.ferraz.playshow.data.remote.model.genres.GenreResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Int?,
    val title: String?,
    @SerialName("original_title") val originalTitle: String?,
    @SerialName("poster_path") val posterPath: String?,
    val overview: String?,
    @SerialName("release_date") val releaseDate: String?,
    val genres: List<GenreResponse?>?,
    @SerialName("origin_country") val originCountry: List<String?>?
)
