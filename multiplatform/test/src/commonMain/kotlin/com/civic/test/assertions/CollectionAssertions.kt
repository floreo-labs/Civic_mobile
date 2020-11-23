package com.civic.test.assertions

import kotlin.test.fail

infix fun Collection<*>.assertSize(expectedSize: Int) {
    if (this.size != expectedSize) {
        fail(message = "Expected a collection of size $expectedSize but was ${this.size}")
    }
}