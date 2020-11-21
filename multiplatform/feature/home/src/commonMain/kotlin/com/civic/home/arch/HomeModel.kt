package com.civic.home.arch

import com.civic.arch.State
import com.civic.arch.StateModel
import com.civic.home.HomePermissions
import com.civic.home.LocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull

class HomeModel(coroutineScope: CoroutineScope,
                private val viewState: State<HomeState>,
                private val homePermissions: HomePermissions,
                private val locationService: LocationService
) : StateModel() {

    fun viewState(onStateUpdate: suspend (HomeState) -> Unit) {
        consumeLocationState()

        collectWith(viewState.flow.filterNotNull(), onStateUpdate)
    }

    fun enableLocationServices() {
        val newState = if (homePermissions.hasLocationPermission()) {
            locationService.startLocationScan()
            HomeState.Loading
        } else {
            HomeState.ShowPermissionUI
        }
        viewState.update(newState)
    }

    fun getCurrentLocation() {
        locationService.startLocationScan()
        viewState.update(HomeState.Loading)
    }

    fun onPermissionDenied() {
        viewState.update(HomeState.Empty)
    }

    private fun consumeLocationState() {

    }
}