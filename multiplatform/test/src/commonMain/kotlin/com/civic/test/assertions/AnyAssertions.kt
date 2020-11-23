package com.civic.test.assertions

import kotlin.contracts.ExperimentalContracts
import kotlin.test.fail

infix fun Any?.assertEquals(target: Any?) {
    kotlin.test.assertEquals(expected = target, actual = this)
}

@ExperimentalContracts
inline fun <reified T> Any.assertIsA() : T {
    if (this !is T) {
        fail(message = "Wanted $this to be of type ${T::class.simpleName} but was ${this::class.simpleName}")
    }
    return this as T
}

@ExperimentalContracts
inline fun <reified T> Any?.assertNotNullIsA(additionalAssertions: (T) -> Unit = { }) {
    val nonNullArgument = assertNotNull()
    val validType = nonNullArgument.assertIsA<T>()
    additionalAssertions(validType)
}

fun Any?.assertNull() {
    kotlin.test.assertNull(actual = this)
}

fun <T : Any> T?.assertNotNull() : T = kotlin.test.assertNotNull(actual = this)