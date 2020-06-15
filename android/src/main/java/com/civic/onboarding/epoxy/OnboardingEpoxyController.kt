package com.civic.onboarding.epoxy

import com.airbnb.epoxy.TypedEpoxyController

class OnboardingEpoxyController : TypedEpoxyController<List<OnboardingItemData>>() {

    override fun buildModels(data: List<OnboardingItemData>?) {
        data?.forEach { itemData ->
            onboardingItem {
                id(itemData.hashCode())
                data(itemData)
            }
        }
    }
}