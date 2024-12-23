package com.ferraz.playshow.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun CoilCachedImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null
) {
    val context = LocalContext.current
    val imageLoader = remember { createImageLoader(context) }

    var isLoading by remember { mutableStateOf(true) }
    var hasFailed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            onState = { state ->
                when (state) {
                    is AsyncImagePainter.State.Loading -> {
                        hasFailed = false
                        isLoading = true
                    }
                    is AsyncImagePainter.State.Success -> {
                        isLoading = false
                        hasFailed = false
                    }
                    else -> {
                        hasFailed = true
                        isLoading = false
                    }
                }
            }
        )
        if (isLoading) {
            CircularProgressIndicator()
        }
        if (hasFailed) {
            Icon(
                imageVector = Icons.Default.PlayCircleOutline,
                contentDescription = "Error loading image",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

private fun createImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.05)
                .build()
        }
        .build()
}