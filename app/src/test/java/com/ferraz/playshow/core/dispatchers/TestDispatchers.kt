package com.ferraz.playshow.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatchers(
    private val testDispatchers: TestDispatcher
) : DispatchersProvider  {
    override val io: CoroutineDispatcher
        get() = testDispatchers
    override val default: CoroutineDispatcher
        get() = testDispatchers
}
