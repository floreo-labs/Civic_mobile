package com.civic.arch

import com.civic.test.assertions.assertFalse
import kotlinx.coroutines.SupervisorJob
import kotlin.test.Test

class CloseableCoroutineScopeTest {

    @Test
    fun `should close coroutine context`() {
        val context = SupervisorJob()

        val scope = CloseableCoroutineScope(context)
        scope.close()

        context.isActive.assertFalse()
    }
}