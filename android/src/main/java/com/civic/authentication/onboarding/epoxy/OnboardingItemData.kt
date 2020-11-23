package com.civic.authentication.onboarding.epoxy

import androidx.annotation.DrawableRes

data class OnboardingItemData(
    @DrawableRes val drawableRes: Int,
    val titleText: String,
    val bodyText: String
)