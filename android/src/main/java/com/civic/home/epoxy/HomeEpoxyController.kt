package com.civic.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.civic.epoxy.extensions.addToController
import com.civic.navigation.AppNavigation

class HomeEpoxyController(private val navigation: AppNavigation) : TypedEpoxyController<List<HomeItem>>() {

    override fun buildModels(data: List<HomeItem>?) {
        data?.forEach { itemData ->
            HomeItemModel(data = itemData, navigation = navigation)
                .id(itemData.hashCode())
                .addToController(this)
        }
    }
}