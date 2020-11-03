package com.civic.home.arch

import com.civic.home.domain.Legislator

sealed class HomeState {
    object Empty: HomeState()
    object Error: HomeState()
    object Loading: HomeState()
    object ShowPermissionUI: HomeState()
    data class Success(val legislators: List<Legislator>) : HomeState()
}