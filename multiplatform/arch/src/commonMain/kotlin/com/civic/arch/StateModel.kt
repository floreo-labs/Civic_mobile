package com.civic.arch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class StateModel {

    val scope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun <T> collectWith(flow: Flow<T>, action: suspend (T) -> Unit) : Job =
        flow.onEach(action)
            .launchIn(scope)

    fun destroy() {
        scope.cancel()
    }
}