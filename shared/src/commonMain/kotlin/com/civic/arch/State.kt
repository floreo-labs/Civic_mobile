package com.civic.arch

import kotlinx.coroutines.flow.Flow

expect class State<T>(initial: T?) {

    val flow: Flow<T?>

    val value: T?

    operator fun plusAssign(value: T)
}