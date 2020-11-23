package com.civic.test.assertions

import kotlin.test.fail

fun <T : Any> List<T>.assertElementAtIndex(index: Int, assertion: (T) -> Unit) {
    val element = getOrNull(index) ?: fail(message = "Expected an element at index $index but was null")
    element.let(assertion)
}