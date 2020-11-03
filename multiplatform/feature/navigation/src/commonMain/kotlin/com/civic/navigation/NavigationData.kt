package com.civic.navigation

sealed class NavigationData {

    object Home: NavigationData()

    object Auth: NavigationData()
}