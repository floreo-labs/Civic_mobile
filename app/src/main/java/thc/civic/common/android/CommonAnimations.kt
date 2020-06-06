package thc.civic.common.android

import android.view.View
import androidx.core.view.isVisible

class CommonAnimations {

    fun fadeOut(view: View, durationMillis: Long) {
        if (view.alpha == 1f || view.isVisible) {
            view.alpha = 1f
            view.animate()
                .setDuration(durationMillis)
                .alpha(0f)
                .withEndAction {
                    view.isVisible = false
                }
        }
    }

    fun fadeIn(view: View, durationMillis: Long) {
        if (view.alpha == 0f || view.isVisible.not()) {
            view.alpha = 0f
            view.animate()
                .setDuration(durationMillis)
                .alpha(1f)
                .withStartAction {
                    view.isVisible = true
                }
        }
    }

    fun scaleFadeIn(view: View, durationMillis: Long) {
        if (view.alpha == 0f || view.scaleX == 0f || view.scaleY == 0f || view.isVisible.not()) {
            view.alpha = 0f
            view.scaleX = 0f
            view.scaleY = 0f
            view.animate()
                .setDuration(durationMillis)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .withStartAction {
                    view.isVisible = true
                }
        }
    }
}