package com.civic.arch

import com.civic.test.assertions.assertEquals
import kotlin.test.Test

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

        state += nextValue

        state.value assertEquals nextValue
    }
}