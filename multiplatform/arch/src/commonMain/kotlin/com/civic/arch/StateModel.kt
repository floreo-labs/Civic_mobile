package com.civic.arch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class StateModel {

    protected val scope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun destroy() {
        scope.close()
    }
}