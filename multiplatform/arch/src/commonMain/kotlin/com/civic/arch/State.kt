package com.civic.arch

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

open class State<T>(initial: T, numValuesToCache: Int = 1) {

    private val mutableStateFlow = MutableSharedFlow<T>(
        replay = numValuesToCache,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply {
        tryEmit(initial)
    }
    private var lastValue: T = initial

    val flow: Flow<T> = mutableStateFlow

    val value: T
        get() = lastValue ?: mutableStateFlow.replayCache.last()

    fun update(value: T) {
        mutableStateFlow.tryEmit(value)
        lastValue = value
    }

    fun reset(nextValue: T? = null) {
        mutableStateFlow.resetReplayCache()

        nextValue?.let(this::update)
    }
}