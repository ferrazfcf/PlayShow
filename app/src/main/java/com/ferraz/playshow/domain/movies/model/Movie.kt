package com.ferraz.playshow.domain.movies.model

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterUrl: String,
    val overview: String,
    val releaseYear: String,
    val genres: String,
    val originCountry: String
)
