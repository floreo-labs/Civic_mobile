package com.civic.common.android

import android.widget.TextView

fun TextView.setDistinctText(text: String) {
    if (this.text != text) {
        this.text = text
    }
}