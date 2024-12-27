package com.ferraz.playshow.di

import android.content.Context
import androidx.room.Room
import com.ferraz.playshow.BuildConfig
import com.ferraz.playshow.data.local.dao.MovieDao
import com.ferraz.playshow.data.local.db.MoviesDatabase
import com.ferraz.playshow.data.remote.model.AuthToken
import com.ferraz.playshow.data.remote.model.BaseUrl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.ferraz.playshow")
class PlayShowModule {

    @Single
    fun engine(): HttpClientEngine {
        return OkHttp.create()
    }

    @Single
    fun baseUrl(): BaseUrl {
        return BaseUrl("https://api.themoviedb.org/3/")
    }

    @Single
    fun authToken(): AuthToken {
        return AuthToken(BuildConfig.API_TOKEN)
    }

    @Single
    fun moviesDb(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            name = "movies_database"
        ).build()
    }

    @Single
    fun movieDao(db: MoviesDatabase): MovieDao {
        return db.movieDao()
    }
}
