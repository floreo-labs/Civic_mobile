package com.civic.home.arch

sealed class HomeState {
    object Empty: HomeState()
    object Error: HomeState()
    object Loading: HomeState()
    object ShowPermissionUI: HomeState()
    data class Success(val id: Int = 0) : HomeState()
}