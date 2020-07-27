package com.civic.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.civic.domain.Legislator
import com.civic.epoxy.extensions.addToController
import com.civic.navigation.AppNavigation

class HomeEpoxyController(private val navigation: AppNavigation) : TypedEpoxyController<List<Legislator>>() {

    override fun buildModels(data: List<Legislator>?) {
        data?.forEach { itemData ->
            HomeItemModel(data = itemData, navigation = navigation)
                .id(itemData.hashCode())
                .addToController(this)
        }
    }
}