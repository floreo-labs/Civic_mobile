package com.civic.navigation

import com.civic.domain.Legislator

interface AppNavigation {

    fun showOnboarding()

    fun showFeed()

    fun showLegislatorDetail(legislator: Legislator)
}