package com.civic.common.android.extensions

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getDrawable(@DrawableRes resId: Int): Drawable =
    requireNotNull(ContextCompat.getDrawable(requireActivity(), resId))