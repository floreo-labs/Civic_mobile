package com.civic.arch

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class State<T>(initial: T? = null, numValuesToCache: Int = 1) {

    private val mutableStateFlow = MutableSharedFlow<T>(
        replay = numValuesToCache,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply {
        initial?.let(this::tryEmit)
    }

    val flow: Flow<T> = mutableStateFlow

    val value: T?
        get() = mutableStateFlow.replayCache.lastOrNull()

    fun update(value: T) {
        mutableStateFlow.tryEmit(value)
    }

    fun reset(nextValue: T? = null) {
        mutableStateFlow.resetReplayCache()

        nextValue?.let(this::update)
    }
}