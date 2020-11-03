package com.civic.arch

import com.civic.test.assertions.assertEmpty
import com.civic.test.assertions.assertEquals
import com.civic.test.assertions.assertFalse
import com.civic.test.coroutines.runScopedTest
import kotlinx.coroutines.flow.flowOf
import kotlin.test.BeforeTest
import kotlin.test.Test

class StateModelTest {

    private lateinit var stateModel: StateModel

    @BeforeTest
    fun setup() {
        stateModel = StateModel()
    }

    @Test
    fun `should observe given flow`() = runScopedTest(stateModel.scope) {
        val value = "something"
        val flow = flowOf(value)
        var updatedValue = ""
        val action: suspend (String) -> Unit = {
            updatedValue = it
        }

        stateModel.collectWith(flow, action)

        updatedValue assertEquals value
    }

    @Test
    fun `should not receive events if scope is cancelled`() = runScopedTest(stateModel.scope) {
        val value = "something"
        val flow = flowOf(value)
        var updatedValue = ""
        val action: suspend (String) -> Unit = {
            updatedValue = it
        }

        stateModel.destroy()
        stateModel.collectWith(flow, action)

        updatedValue.assertEmpty()
    }

    @Test
    fun `should not receive events after scope is cancelled`() = runScopedTest(stateModel.scope) {
        val value = "something"
        val flow = flowOf(value)
        var updatedValue = ""
        val action: suspend (String) -> Unit = {
            updatedValue = it
        }

        val job = stateModel.collectWith(flow, action)
        updatedValue assertEquals value
        stateModel.destroy()

        job.isActive.assertFalse()
    }
}