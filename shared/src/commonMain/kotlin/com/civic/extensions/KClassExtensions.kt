package com.civic.extensions

import kotlin.reflect.KClass

val KClass<*>.nonNullSimpleName
    get() = requireNotNull(simpleName)