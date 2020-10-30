package com.civic.arch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class State<T>(initial: T) {

    private val mutableStateFlow = MutableStateFlow(initial)

    val flow: StateFlow<T> = mutableStateFlow

    val value: T
        get() = mutableStateFlow.value

    operator fun plusAssign(value: T) {
        mutableStateFlow.value = value
    }
}