package com.civic.authentication.onboarding.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.civic.epoxy.extensions.addToController

class OnboardingEpoxyController : TypedEpoxyController<List<OnboardingItemData>>() {

    override fun buildModels(data: List<OnboardingItemData>?) {
        data?.forEach { itemData ->
            OnboardingItemModel(data = itemData)
                .id(itemData.hashCode())
                .addToController(this)
        }
    }
}