package com.civic.arch

import app.cash.turbine.test
import com.civic.test.assertions.assertEquals
import com.civic.test.coroutines.runTest
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StateTest {

    private val initialValue = ""
    private val state = State(initialValue)

    @Test
    fun `should contain initial value`() {
        state.value assertEquals initialValue
    }

    @Test
    fun `should update internal state flow`() {
        val nextValue = "bro"

        state.update(nextValue)

        state.value assertEquals nextValue
    }

    @Test
    fun `should reset internal cache`() = runTest {
        val values = listOf("1", "2", "3")

        values.forEach { value -> state.update(value) }
        state.reset()

        state.flow.test {
            expectNoEvents()
        }
    }

    @Test
    fun `should reset internal cache but keep last emitted value`() = runTest {
        val values = listOf("1", "2", "3")

        values.forEach { value -> state.update(value) }
        state.reset()

        state.value assertEquals values.last()
    }

    @Test
    fun `should reset internal cache but keep next value`() = runTest {
        val values = listOf("1", "2", "3")
        val lastValue = "4"

        values.forEach { value -> state.update(value) }
        state.reset(nextValue = lastValue)

        state.value assertEquals lastValue
    }
}