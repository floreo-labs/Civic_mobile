package com.civic.feed.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import com.civic.arch.State
import com.civic.arch.StateModel
import com.civic.domain.SharedAddress
import com.civic.feed.FeedPermissions
import com.civic.location.LocationService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlin.coroutines.CoroutineContext

class FeedModel(coroutineScope: CoroutineScope,
                workerContext: CoroutineContext,
                private val sharedAddressState: State<SharedAddress>,
                private val viewState: State<FeedState>,
                private val feedPermissions: FeedPermissions,
                private val locationService: LocationService
) : StateModel(coroutineScope) {

    fun viewState(onStateUpdate: suspend (FeedState) -> Unit) {
        collectWith(sharedAddressState.flow.filterNotNull()) {
            viewState += FeedState.Success(1)
        }

        collectWith(viewState.flow.filterNotNull(), onStateUpdate)
    }

    fun enableLocationServices() {
        val newState = if (feedPermissions.hasLocationPermission()) {
            locationService.startLocationScan()
            FeedState.Loading
        } else {
            FeedState.ShowPermissionUI
        }
        viewState += newState
    }

    fun getCurrentLocation() {
        locationService.startLocationScan()
        viewState += FeedState.Loading
    }

    fun onPermissionDenied() {
        viewState += FeedState.Empty
    }
}