package com.ferraz.playshow.presentation.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.domain.movies.usecase.details.MovieDetails
import com.ferraz.playshow.domain.movies.usecase.mylist.add.AddToMyList
import com.ferraz.playshow.domain.movies.usecase.mylist.get.IsMovieOnMyList
import com.ferraz.playshow.domain.movies.usecase.mylist.remove.RemoveFromMyList
import com.ferraz.playshow.presentation.moviedetails.model.MovieDetailsAction
import com.ferraz.playshow.presentation.moviedetails.model.MovieDetailsState
import com.ferraz.playshow.presentation.moviedetails.model.MyListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory(binds = [MovieDetailsViewModel::class])
class MovieDetailsViewModel(
    @InjectedParam private val movieId: Int,
    private val movieDetails: MovieDetails,
    private val isMovieOnMyList: IsMovieOnMyList,
    private val addToMyList: AddToMyList,
    private val removeFromMyList: RemoveFromMyList,
    private val dispatchers: DispatchersProvider
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state.onStart {
        onAction(MovieDetailsAction.LoadDetails)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieDetailsState()
    )

    fun onAction(action: MovieDetailsAction) {
        when (action) {
            is MovieDetailsAction.LoadDetails -> loadDetails(movieId)
            MovieDetailsAction.AddToMyList -> addToMyList()
            MovieDetailsAction.RemoveFromMyList -> removeFromMyList()
            is MovieDetailsAction.NavigateBack -> Unit
        }
    }

    private fun loadDetails(movieId: Int) {
        if (state.value.isLoading) return
        viewModelScope.launch(dispatchers.default) {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    error = null
                )
            }

            val result = movieDetails(movieId)
            _state.update { state ->
                updateMovieDetailsState(state, result)
            }
            checkIsMovieOnMyList(movieId)
        }
    }

    private fun updateMovieDetailsState(
        state: MovieDetailsState,
        result: Result<Movie>
    ): MovieDetailsState {
        var error: String? = null
        var movie: Movie? = null

        result.fold(
            onSuccess = { movie = it },
            onFailure = { error = it.message }
        )

        return state.copy(
            isLoading = false,
            movie = movie,
            error = error
        )
    }

    private fun checkIsMovieOnMyList(movieId: Int) {
        viewModelScope.launch(dispatchers.default) {
            val result = isMovieOnMyList(movieId)
            _state.update { state ->
                result.fold(
                    onSuccess = { state.copy(myListState = MyListState.ADDED) },
                    onFailure = { state.copy(myListState = MyListState.NOT_ADDED) }
                )
            }
        }
    }

    private fun addToMyList() {
        val movie = state.value.movie ?: return
        if (state.value.isLoading) return

        viewModelScope.launch(dispatchers.default) {
            _state.update { state ->
                state.copy(myListState = MyListState.PROCESSING)
            }

            val result = addToMyList(movie)
            _state.update { state ->
                result.fold(
                    onSuccess = { state.copy(myListState = MyListState.ADDED) },
                    onFailure = { state.copy(myListState = MyListState.NOT_ADDED) }
                )
            }
        }
    }

    private fun removeFromMyList() {
        val movie = state.value.movie ?: return
        if (state.value.movie == null) return

        viewModelScope.launch(dispatchers.default) {
            _state.update { state ->
                state.copy(myListState = MyListState.PROCESSING)
            }

            val result = removeFromMyList(movie)
            _state.update { state ->
                result.fold(
                    onSuccess = { state.copy(myListState = MyListState.NOT_ADDED) },
                    onFailure = { state.copy(myListState = MyListState.ADDED) }
                )
            }
        }
    }
}
