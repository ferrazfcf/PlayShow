package com.ferraz.playshow.presentation.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.domain.movies.model.MovieItem
import com.ferraz.playshow.presentation.components.CoilCachedImage
import com.ferraz.playshow.presentation.mylist.model.MyListAction
import com.ferraz.playshow.presentation.theme.PlayShowTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MyListScreen(
    moviesList: ImmutableList<MovieItem>,
    onAction: (MyListAction) -> Unit
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
            moviesList
        ) { item ->
            CoilCachedImage(
                modifier = Modifier
                    .height(280.dp)
                    .clickable {
                        onAction(MyListAction.OpenMovieDetails(item.id))
                    },
                imageUrl = item.posterUrl,
                contentDescription = item.title
            )
        }

        if (moviesList.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "No movies found",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyListScreenPreview() {
    val moviesList = mutableListOf<MovieItem>()
    for (v in PREVIEW_RANGE) {
        moviesList.add(MovieItem(v, "Movie Title $v", ""))
    }
    PlayShowTheme {
        MyListScreen(
            moviesList.toImmutableList(),
            onAction = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyPreview() {
    PlayShowTheme {
        MyListScreen(
            emptyList<MovieItem>().toImmutableList(),
            onAction = { }
        )
    }
}

private const val RANGE_START = 1
private const val RANGE_END = 10
private val PREVIEW_RANGE = RANGE_START..RANGE_END
