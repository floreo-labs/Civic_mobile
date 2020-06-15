package com.civic.home.arch

import kotlinx.coroutines.CoroutineScope
import com.civic.arch.State
import com.civic.arch.StateModel
import com.civic.domain.SharedAddress
import com.civic.home.HomePermissions
import com.civic.location.LocationService
import kotlin.coroutines.CoroutineContext

class HomeModel(coroutineScope: CoroutineScope,
                workerContext: CoroutineContext,
                private val sharedAddressState: State<SharedAddress>,
                private val viewState: State<HomeState>,
                private val homePermissions: HomePermissions,
                private val locationService: LocationService
) : StateModel(coroutineScope) {

    fun viewState(onStateUpdate: suspend (HomeState) -> Unit) {
        collectWith(sharedAddressState.flow.filterNotNull()) {
            viewState += HomeState.Success(1)
        }

        collectWith(viewState.flow.filterNotNull(), onStateUpdate)
    }

    fun enableLocationServices() {
        val newState = if (homePermissions.hasLocationPermission()) {
            locationService.startLocationScan()
            HomeState.Loading
        } else {
            HomeState.ShowPermissionUI
        }
        viewState += newState
    }

    fun getCurrentLocation() {
        locationService.startLocationScan()
        viewState += HomeState.Loading
    }

    fun onPermissionDenied() {
        viewState += HomeState.Empty
    }
}