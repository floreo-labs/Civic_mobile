package com.civic.common.android.extensions

import androidx.fragment.app.Fragment

fun Fragment.getStringArgument(key: String): String =
    requireNotNull(
        requireNotNull(
            arguments
        ).getString(key)
    )