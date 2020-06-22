package com.civic.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController

class HomeEpoxyController : TypedEpoxyController<List<HomeItem>>() {

    override fun buildModels(data: List<HomeItem>?) {
        data?.forEach { itemData ->
            homeItem {
                id(itemData.hashCode())
                data(itemData)
            }
        }
    }
}