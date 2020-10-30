package com.civic.common.extensions

val Any.exhaust
    get() = Unit

inline fun <reified T> Any?.takeIfInstance(ifCastSucceeds: (T) -> Unit) {
    if (this is T) ifCastSucceeds(this)
}