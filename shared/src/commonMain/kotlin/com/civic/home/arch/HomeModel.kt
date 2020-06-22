package com.civic.home.arch

import com.apollographql.apollo.ApolloClient
import com.civic.arch.State
import com.civic.arch.StateModel
import com.civic.domain.UserLocation
import com.civic.home.HomePermissions
import com.civic.home.LocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlin.coroutines.CoroutineContext

class HomeModel(coroutineScope: CoroutineScope,
                workerContext: CoroutineContext,
                private val apolloClient: ApolloClient,
                private val userLocationState: State<UserLocation>,
                private val viewState: State<HomeState>,
                private val homePermissions: HomePermissions,
                private val locationService: LocationService
) : StateModel(coroutineScope) {

    fun viewState(onStateUpdate: suspend (HomeState) -> Unit) {
        collectWith(userLocationState.flow.filterNotNull()) {
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