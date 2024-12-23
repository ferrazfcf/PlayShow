package com.ferraz.playshow.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
