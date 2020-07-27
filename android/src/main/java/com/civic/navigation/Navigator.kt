package com.civic.navigation

import com.civic.domain.Legislator

interface Navigator {

    fun showOnboarding()

    fun showFeed()

    fun showLegislatorDetail(legislator: Legislator)
}