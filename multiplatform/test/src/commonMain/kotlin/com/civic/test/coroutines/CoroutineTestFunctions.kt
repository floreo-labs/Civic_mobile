package com.civic.test.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun runScopedTest(scope: CoroutineScope, block: suspend () -> Unit) {
    scope.launch { block() }
}