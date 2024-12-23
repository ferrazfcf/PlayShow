package com.ferraz.playshow.presentation.moviedetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.domain.movies.model.Movie
import com.ferraz.playshow.presentation.components.CoilCachedImage
import com.ferraz.playshow.presentation.components.ErrorRetry

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit
) {

    when {
        state.isLoading -> LoadingScreen()
        state.movie != null -> ContentScreen(state.movie, onAction)
        else -> ErrorRetry(
            error = state.error ?: "Invalid movie data",
            color = MaterialTheme.colorScheme.error,
            onRetry = { onAction(MovieDetailsAction.LoadDetails) }
        )
    }
}

@Composable
private fun ContentScreen(movie: Movie, onAction: (MovieDetailsAction) -> Unit) {
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

            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Back",
                modifier = Modifier.size(40.dp).clickable { onAction(MovieDetailsAction.NavigateBack) }
            )
        }

        CoilCachedImage(
            modifier = Modifier.size(160.dp, 280.dp),
            imageUrl = movie.posterPath,
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
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
