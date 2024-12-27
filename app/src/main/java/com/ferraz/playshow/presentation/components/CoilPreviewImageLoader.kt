package com.ferraz.playshow.presentation.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import coil3.ComponentRegistry
import coil3.ImageLoader
import coil3.asImage
import coil3.decode.DataSource
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.Disposable
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.ImageResult
import coil3.request.SuccessResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlin.random.Random

class CoilPreviewImageLoader(context: Context): ImageLoader {
    override val components: ComponentRegistry = ImageLoader.Builder(context).build().components
    override val defaults: ImageRequest.Defaults = ImageLoader.Builder(context).build().defaults
    override val diskCache: DiskCache? = null
    override val memoryCache: MemoryCache? = null

    override fun enqueue(request: ImageRequest): Disposable {
        val result = newResult(request)
        return object : Disposable {
            override val job: Deferred<ImageResult> = CompletableDeferred(result)
            override val isDisposed: Boolean = false
            override fun dispose() {}
        }
    }

    override suspend fun execute(request: ImageRequest): ImageResult {
        return newResult(request)
    }

    override fun newBuilder(): ImageLoader.Builder {
        throw UnsupportedOperationException("Preview ImageLoader does not support builder calls")
    }

    override fun shutdown() { }

    private fun newResult(request: ImageRequest): ImageResult {
        val random = Random.nextInt(2)
        return when (random) {
            0 -> SuccessResult(
                image = ColorDrawable(Color.RED).asImage(),
                request = request,
                dataSource = DataSource.MEMORY
            )
            else -> ErrorResult (
                image = null,
                request = request,
                throwable = IllegalStateException("Preview ImageLoader does not support preview images")
            )
        }
    }
}
