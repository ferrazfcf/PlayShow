package com.ferraz.playshow.core.extensions

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
fun LazyGridState.OnBottomReached(
    shouldLoadNextPage: Boolean,
    loadMore: () -> Unit
) {
    if (shouldLoadNextPage.not()) return

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }.collect {
            if (it.not()) return@collect
            if (layoutInfo.totalItemsCount != 0) {
                loadMore()
            }
        }
    }
}
