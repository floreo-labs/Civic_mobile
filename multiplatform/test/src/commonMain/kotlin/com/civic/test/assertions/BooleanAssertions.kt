package com.civic.test.assertions

fun Boolean.assertTrue() {
    kotlin.test.assertTrue(this)
}

fun Boolean.assertFalse() {
    kotlin.test.assertFalse(this)
}
