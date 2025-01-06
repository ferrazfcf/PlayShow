package com.ferraz.playshow.data.local.dao

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ferraz.playshow.data.local.db.MoviesDatabase
import com.ferraz.playshow.data.local.model.MovieEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.VANILLA_ICE_CREAM],
    manifest = Config.NONE
)
class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: MoviesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        movieDao = db.movieDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetMovie() = runTest {
        val movie = MovieEntity(
            id = 1,
            title = "Test Movie",
            originalTitle = "Test Original Title",
            posterUrl = "test_poster_path",
            overview = "This is a test movie",
            releaseYear = "2023",
            genres = "Test Genre",
            originCountry = "Test Country"
        )
        movieDao.insertMovie(movie)
        val retrievedMovie = movieDao.getMovieById(movie.id)
        expectThat(retrievedMovie).isEqualTo(movie)
    }
}
