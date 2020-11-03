package com.civic.test.coroutines

import kotlinx.coroutines.runBlocking

actual fun runTest(block: suspend () -> Unit) = runBlocking {
    block()
}