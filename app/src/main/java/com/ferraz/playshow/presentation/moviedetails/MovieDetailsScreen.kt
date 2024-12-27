package com.ferraz.playshow.presentation.moviedetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.presentation.components.CoilCachedImage
import com.ferraz.playshow.presentation.components.ErrorRetry
import com.ferraz.playshow.presentation.components.IconTextButton
import com.ferraz.playshow.presentation.moviedetails.model.MovieDetailsAction
import com.ferraz.playshow.presentation.moviedetails.model.MovieDetailsState
import com.ferraz.playshow.presentation.moviedetails.model.MyListState
import com.ferraz.playshow.presentation.theme.PlayShowTheme

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit
) {

    when {
        state.isLoading -> LoadingScreen()
        state.movie != null -> ContentScreen(state.movie, state.myListState, onAction)
        else -> ErrorRetry(
            modifier = Modifier.fillMaxSize(),
            error = state.error ?: "Invalid movie data",
            color = MaterialTheme.colorScheme.error,
            onRetry = { onAction(MovieDetailsAction.LoadDetails) }
        )
    }
}

@Composable
private fun ContentScreen(
    movie: Movie,
    myListState: MyListState,
    onAction: (MovieDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowCircleLeft,
                contentDescription = "Back",
                modifier = Modifier.size(40.dp).clickable { onAction(MovieDetailsAction.NavigateBack) }
            )

            MyListInteraction(myListState, onAction)
        }

        CoilCachedImage(
            modifier = Modifier.size(160.dp, 280.dp),
            imageUrl = movie.posterUrl,
            contentDescription = movie.title
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = movie.originalTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Release Year: ${movie.releaseYear}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Genres: ${movie.genres}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Origin Country: ${movie.originCountry}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun MyListInteraction(
    myListState: MyListState,
    onAction: (MovieDetailsAction) -> Unit
) {
    AnimatedContent(myListState, label = "My List Interaction") { state ->
        when (state) {
            MyListState.ADDED -> IconTextButton(
                label = "In My List",
                imageVector = Icons.Default.Star,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                tint = MaterialTheme.colorScheme.onSecondary,
                enabled = true
            ) { onAction(MovieDetailsAction.RemoveFromMyList) }
            MyListState.NOT_ADDED -> IconTextButton(
                label = "Add To My List",
                imageVector = Icons.Default.Add,
                backgroundColor = MaterialTheme.colorScheme.primary,
                tint = MaterialTheme.colorScheme.onPrimary,
                enabled = true
            ) { onAction(MovieDetailsAction.AddToMyList) }
            MyListState.PROCESSING -> IconTextButton(
                label = "Adding To My List",
                imageVector = Icons.Default.ArrowOutward,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                tint = MaterialTheme.colorScheme.onSecondary,
                enabled = false,
                onClick = { }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsSuccessPreview() {
    val movie = Movie(
        id = 1,
        title = "Movie Title",
        originalTitle = "Original Title",
        posterUrl = "",
        overview = "Overview",
        releaseYear = "2023",
        genres = "Action, Adventure",
        originCountry = "US",
    )
    PlayShowTheme {
        MovieDetailsScreen(
            state = MovieDetailsState(
                movie = movie,
                myListState = MyListState.NOT_ADDED
            ),
            onAction = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsLoadingPreview() {
    PlayShowTheme {
        MovieDetailsScreen(
            state = MovieDetailsState(isLoading = true),
            onAction = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsErrorPreview() {
    PlayShowTheme {
        MovieDetailsScreen(
            state = MovieDetailsState(error = "Preview Error"),
            onAction = { }
        )
    }
}
