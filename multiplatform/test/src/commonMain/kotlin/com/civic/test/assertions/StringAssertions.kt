package com.civic.test.assertions

fun String.assertEmpty() {
    kotlin.test.assertEquals(expected = "", actual = this)
}