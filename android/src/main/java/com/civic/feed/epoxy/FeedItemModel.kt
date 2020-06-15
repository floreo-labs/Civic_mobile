package com.civic.feed.epoxy

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.civic.R
import com.civic.epoxy.KotlinHolder

@EpoxyModelClass(layout = R.layout.fragment_feed_item)
abstract class FeedItemModel : EpoxyModelWithHolder<FeedItemModel.ViewHolder>() {

    override fun bind(holder: ViewHolder) {

    }

    class ViewHolder: KotlinHolder() {

    }
}