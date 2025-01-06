package com.ferraz.playshow.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ferraz.playshow.data.local.dao.MovieDao
import com.ferraz.playshow.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
