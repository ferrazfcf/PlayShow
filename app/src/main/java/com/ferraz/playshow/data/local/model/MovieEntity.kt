package com.ferraz.playshow.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    val overview: String,
    @ColumnInfo(name = "release_year")
    val releaseYear: String,
    val genres: String,
    @ColumnInfo(name = "origin_country")
    val originCountry: String
)
