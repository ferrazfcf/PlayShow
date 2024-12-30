package com.ferraz.playshow.presentation.moviedetails

import app.cash.turbine.test
import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.core.dispatchers.TestDispatchers
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.domain.movies.usecase.details.MovieDetails
import com.ferraz.playshow.domain.movies.usecase.mylist.add.AddToMyList
import com.ferraz.playshow.domain.movies.usecase.mylist.get.IsMovieOnMyList
import com.ferraz.playshow.domain.movies.usecase.mylist.remove.RemoveFromMyList
import com.ferraz.playshow.presentation.moviedetails.model.MovieDetailsState
import com.ferraz.playshow.presentation.moviedetails.model.MyListState
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest : KoinTest {

    private val movieId = 1
    private val testDispatcher = StandardTestDispatcher()
    private val dispatchers = TestDispatchers(testDispatcher)

    private val viewModel: MovieDetailsViewModel by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<DispatchersProvider> { dispatchers }
                factory { MovieDetailsViewModel(movieId, get(), get(), get(), get(), get()) }
            }
        )
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz, relaxed = true)
    }

    private lateinit var movieDetails: MovieDetails
    private lateinit var isMovieOnMyList: IsMovieOnMyList
    private lateinit var addToMyList: AddToMyList
    private lateinit var removeFromMyList: RemoveFromMyList

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieDetails = declareMock()
        isMovieOnMyList = declareMock()
        addToMyList = declareMock()
        removeFromMyList = declareMock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `test loadDetails success`() = runTest {
        // given
        val movieMock = mockkClass(Movie::class)
        coEvery { movieDetails(movieId) } returns Result.success(movieMock)
        coEvery { isMovieOnMyList(movieId) } returns Result.success(Unit)

        // when
        val states = mutableListOf<MovieDetailsState>()
        viewModel.state.test {
            states.add(awaitItem())
            states.add(awaitItem())
            states.add(awaitItem())
        }

        // then
        expectThat(states[0]).isA<MovieDetailsState>()
            .and {
                get { isLoading }.isEqualTo(false)
                get { movie }.isNull()
                get { error }.isNull()
                get { myListState }.isEqualTo(MyListState.PROCESSING)
            }
        expectThat(states[1]).isA<MovieDetailsState>()
            .and {
                get { isLoading }.isEqualTo(false)
                get { movie }.isEqualTo(movieMock)
                get { error }.isNull()
                get { myListState }.isEqualTo(MyListState.PROCESSING)
            }
        expectThat(states[2]).isA<MovieDetailsState>()
            .and {
                get { isLoading }.isEqualTo(false)
                get { movie }.isEqualTo(movieMock)
                get { error }.isNull()
                get { myListState }.isEqualTo(MyListState.ADDED)
            }
    }
}
