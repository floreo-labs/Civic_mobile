package com.civic.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.civic.epoxy.extensions.addToController

class HomeEpoxyController : TypedEpoxyController<List<HomeItem>>() {

    override fun buildModels(data: List<HomeItem>?) {
        data?.forEach { itemData ->
            HomeItemModel(data = itemData)
                .id(itemData.hashCode())
                .addToController(this)
        }
    }
}