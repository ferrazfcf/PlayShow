package com.ferraz.playshow.presentation.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferraz.playshow.core.dispatchers.PlayShowDispatchers
import com.ferraz.playshow.domain.movies.model.MovieItem
import com.ferraz.playshow.domain.movies.usecase.mylist.getall.MyList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Factory

@Factory(binds = [MyListViewModel::class])
class MyListViewModel(
    private val getMyList: MyList,
    private val dispatchers: PlayShowDispatchers
) : ViewModel() {

    val state: StateFlow<ImmutableList<MovieItem>>
        get() = getMyList()
            .map { it.toImmutableList() }
            .flowOn(dispatchers.default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STATE_STOP_TIMEOUT),
                initialValue = emptyList<MovieItem>().toImmutableList()
            )

    companion object {
        private const val STATE_STOP_TIMEOUT: Long = 5_000
    }
}
