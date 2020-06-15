package com.civic.onboarding.epoxy

import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.civic.R
import com.civic.epoxy.KotlinHolder
import com.civic.common.android.setDistinctText

@EpoxyModelClass(layout = R.layout.fragment_onboarding_item)
abstract class OnboardingItemModel: EpoxyModelWithHolder<OnboardingItemModel.ViewHolder>() {

    @EpoxyAttribute
    lateinit var data: OnboardingItemData

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        holder.setData(data)
    }

    class ViewHolder : KotlinHolder() {

        val image by bind<ImageView>(R.id.fragment_onboarding_item_image)
        val title by bind<TextView>(R.id.fragment_onboarding_item_title)
        val body by bind<TextView>(R.id.fragment_onboarding_item_body)

        fun setData(data: OnboardingItemData) {
            image.setImageResource(data.drawableRes)
            title.setDistinctText(data.titleText)
            body.setDistinctText(data.bodyText)
        }
    }
}