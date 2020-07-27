package com.civic.epoxy.extensions

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel

fun EpoxyModel<*>.addToController(controller: EpoxyController) = controller.add(this)