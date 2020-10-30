package com.civic.common.extensions

import kotlin.reflect.KClass

val KClass<*>.nonNullSimpleName
    get() = requireNotNull(simpleName)