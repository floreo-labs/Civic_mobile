package com.civic.common.android

import android.text.TextPaint
import android.text.style.ClickableSpan
import androidx.annotation.ColorInt

abstract class HighlightableClickableSpan(@ColorInt private val textColor: Int) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = textColor
        ds.isUnderlineText = true
    }
}