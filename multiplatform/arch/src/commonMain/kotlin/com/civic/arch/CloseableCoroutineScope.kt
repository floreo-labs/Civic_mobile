package com.civic.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class CloseableCoroutineScope(override val coroutineContext: CoroutineContext) : CoroutineScope {

    fun close() {
        coroutineContext.cancel()
    }
}