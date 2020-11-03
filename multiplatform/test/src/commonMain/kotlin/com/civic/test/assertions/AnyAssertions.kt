package com.civic.test.assertions

infix fun Any.assertEquals(target: Any) {
    kotlin.test.assertEquals(expected = target, actual = this)
}