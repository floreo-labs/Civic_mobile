package com.civic.home.arch

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.civic.arch.State
import com.civic.arch.StateModel
import com.civic.domain.Legislator
import com.civic.domain.UserLocation
import com.civic.home.HomePermissions
import com.civic.home.LocationService
import com.civic.queries.YourLegislatorsQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeModel(coroutineScope: CoroutineScope,
                private val workerContext: CoroutineContext,
                private val apolloClient: ApolloClient,
                private val userLocationState: State<UserLocation>,
                private val viewState: State<HomeState>,
                private val homePermissions: HomePermissions,
                private val locationService: LocationService
) : StateModel(coroutineScope) {

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
        viewState += newState
    }

    fun getCurrentLocation() {
        locationService.startLocationScan()
        viewState += HomeState.Loading
    }

    fun onPermissionDenied() {
        viewState += HomeState.Empty
    }

    private fun consumeLocationState() {
        coroutineScope.launch(context = workerContext) {
            val flow = userLocationState.flow
                .filterNotNull()
                .map { userLocation ->
                    YourLegislatorsQuery(
                        latitude = userLocation.latitude,
                        longitude = userLocation.longitude
                    )
                }
                .flatMapConcat { yourLegislatorsQuery ->
                    apolloClient.query(yourLegislatorsQuery).execute()
                }
                .catch { exception ->
                    viewState += HomeState.Error
                }
                .map { response ->
                    response.mapToViewState()
                }
            collectWith(flow) { homeState ->
                viewState += homeState
            }
        }
    }

    private fun Response<YourLegislatorsQuery.Data>.mapToViewState() =
        HomeState.Success(legislators = data!!.legislators!!.edges.map { edge ->
            edge!!.node!!.run {
                Legislator(
                    name = name!!,
                    imageUrl = image!!.replace("http://", "https://")
                )
            }
        })
}