package com.ferraz.playshow.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
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
    val imageLoader = rememberImageLoader(context)

    var isLoading by remember { mutableStateOf(true) }
    var hasFailed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface),
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

@Composable
private fun rememberImageLoader(context: Context): ImageLoader {
    val isInPreview = LocalInspectionMode.current
    return remember {
        if (isInPreview) {
            CoilPreviewImageLoader(context)
        } else {
            createImageLoader(context)
        }
    }
}

private fun createImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, MEMORY_CACHE_PERCENTAGE)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve(DISK_CACHE_DIRECTORY))
                .maxSizePercent(DISK_CACHE_PERCENTAGE)
                .build()
        }
        .build()
}

@Preview(showBackground = true, backgroundColor = 0xFFFF0000)
@Composable
private fun CoilCachedImagePreview() {
    CoilCachedImage(
        modifier = Modifier.size(120.dp),
        imageUrl = "https://example.com/image.jpg",
        contentDescription = "Example Image"
    )
}

private const val MEMORY_CACHE_PERCENTAGE = 0.25
private const val DISK_CACHE_PERCENTAGE = 0.05
private const val DISK_CACHE_DIRECTORY = "image_cache"
