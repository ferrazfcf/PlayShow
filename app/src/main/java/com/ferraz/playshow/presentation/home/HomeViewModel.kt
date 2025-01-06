package com.ferraz.playshow.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferraz.playshow.core.dispatchers.DispatchersProvider
import com.ferraz.playshow.domain.movies.model.MovieItem
import com.ferraz.playshow.domain.movies.usecase.list.MoviesList
import com.ferraz.playshow.presentation.home.model.HomeAction
import com.ferraz.playshow.presentation.home.model.HomeState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory(binds = [HomeViewModel::class])
class HomeViewModel(
    private val moviesList: MoviesList,
    private val dispatchers: DispatchersProvider
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.onStart {
        onAction(HomeAction.LoadMovies)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_STOP_TIMEOUT),
        initialValue = HomeState()
    )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.LoadMovies -> loadMovies()
            is HomeAction.LoadMoreMovies -> loadMoreMovies()
            is HomeAction.OpenMovieDetails -> Unit
        }
    }

    private fun loadMoreMovies() {
        if (state.value.isLoading) return
        var currentPage = state.value.currentPage
        if (state.value.moviesList.isEmpty()) {
            currentPage = FIRST_PAGE
        } else if (state.value.errorMessage == null) {
            currentPage++
        }
        loadMovies(currentPage)
    }

    private fun loadMovies(page: Int = 1) {
        if (state.value.isLoading) return
        viewModelScope.launch(dispatchers.default) {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val result = moviesList(page)
            _state.update { state ->
                updateHomeState(state, page, result)
            }
        }
    }

    private fun updateHomeState(
        state: HomeState,
        page: Int,
        result: Result<List<MovieItem>>
    ): HomeState {
        var error: String? = null
        val moviesList = mutableListOf<MovieItem>()
        if (page > 1) moviesList.addAll(state.moviesList)

        result.fold(
            onSuccess = { moviesList.addAll(it) },
            onFailure = { error = it.message }
        )

        return state.copy(
            isLoading = false,
            moviesList = moviesList.toImmutableList(),
            currentPage = page,
            errorMessage = error
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val STATE_STOP_TIMEOUT: Long = 5_000
    }
}
