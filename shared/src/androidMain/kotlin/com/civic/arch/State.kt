package com.civic.arch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual class State<T> actual constructor(initial: T?) {

    private val mutableStateFlow: MutableStateFlow<T?> = MutableStateFlow(initial)

    actual val flow: Flow<T?> = mutableStateFlow

    actual val value: T?
        get() = mutableStateFlow.value

    actual operator fun plusAssign(value: T) {
        mutableStateFlow.value = value
    }
}