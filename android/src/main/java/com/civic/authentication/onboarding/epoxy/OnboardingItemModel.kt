package com.civic.authentication.onboarding.epoxy

import android.widget.ImageView
import android.widget.TextView
import com.civic.R
import com.civic.common.android.setDistinctText
import com.civic.epoxy.KotlinModel

data class OnboardingItemModel(private val data: OnboardingItemData): KotlinModel(R.layout.fragment_onboarding_item) {

    val image by bind<ImageView>(R.id.fragment_onboarding_item_image)
    val title by bind<TextView>(R.id.fragment_onboarding_item_title)
    val body by bind<TextView>(R.id.fragment_onboarding_item_body)

    override fun bind() {
        image.setImageResource(data.drawableRes)
        title.setDistinctText(data.titleText)
        body.setDistinctText(data.bodyText)
    }
}