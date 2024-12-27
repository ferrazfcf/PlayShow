package com.ferraz.playshow.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.presentation.components.CoilCachedImage
import com.ferraz.playshow.presentation.components.ErrorRetry
import com.ferraz.playshow.presentation.extensions.OnBottomReached

@Composable
fun HomeScreen(
    homeState: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = gridState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            homeState.moviesList
        ) { item ->
            CoilCachedImage(
                modifier = Modifier
                    .height(280.dp)
                    .clickable {
                        onAction(HomeAction.OpenMovieDetails(item.id))
                    },
                imageUrl = item.posterPath,
                contentDescription = item.title
            )
        }

        if (homeState.isLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (homeState.errorMessage != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ErrorRetry(
                    error = homeState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    onRetry = { onAction(HomeAction.LoadMoreMovies) }
                )
            }
        }
    }

    gridState.OnBottomReached(homeState.isLoading.not() && homeState.errorMessage == null) {
        onAction(HomeAction.LoadMoreMovies)
    }
}
