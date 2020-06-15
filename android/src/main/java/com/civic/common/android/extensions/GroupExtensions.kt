package com.civic.common.android.extensions

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible

fun Group.setAllVisible(root: ConstraintLayout) {
    isVisible = true
    updatePreLayout(root)
}

fun Group.setAllGone(root: ConstraintLayout) {
    isVisible = false
    updatePreLayout(root)
}