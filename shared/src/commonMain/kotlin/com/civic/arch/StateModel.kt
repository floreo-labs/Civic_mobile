package com.civic.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * [coroutineScope] should be lifecycle-component bound
 */
open class StateModel(protected val coroutineScope: CoroutineScope) {

    private val jobs = mutableListOf<Job>()

    fun <T> collectWith(state: State<T>, action: suspend (T?) -> Unit) {
        jobs += coroutineScope.launch {
            state.flow.collect(action)
        }
    }

    fun <T> collectWith(flow: Flow<T>, action: suspend (T) -> Unit) {
        jobs += coroutineScope.launch {
            flow.collect(action)
        }
    }

    fun destroy() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}