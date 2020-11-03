package com.civic.test.assertions

infix fun Any?.assertEquals(target: Any?) {
    kotlin.test.assertEquals(expected = target, actual = this)
}

inline fun <reified T> Any.assertIsA() {
    (this is T).assertTrue()
}

inline fun <reified T> Any?.assertNotNullIsA(additionalAssertions: (T) -> Unit = { }) {
    val result = assertNotNull()

    if (this is T) {
        additionalAssertions(this)
    } else {
        kotlin.test.fail("Expected $this to be ${T::class.simpleName} but was ${result::class.simpleName}")
    }
}

fun Any?.assertNull() {
    kotlin.test.assertNull(actual = this)
}

fun <T : Any> T?.assertNotNull() : T = kotlin.test.assertNotNull(actual = this)