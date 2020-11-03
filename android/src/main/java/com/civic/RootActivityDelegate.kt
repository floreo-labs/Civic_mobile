package com.civic

import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.civic.common.extensions.exhaust
import com.civic.delegate.ComponentDelegate
import com.civic.home.LocationService
import com.civic.navigation.AppNavigation
import com.civic.navigation.NavigationData
import com.civic.navigation.NavigationModel

class RootActivityDelegate(
                           private val lifecycle: Lifecycle,
                           private val deviceLocation: LocationService,
                           private val navigationModel: NavigationModel,
                           private val appNavigation: AppNavigation) : ComponentDelegate(), DefaultLifecycleObserver {

    override fun onViewAttached(savedState: Bundle?) {
        lifecycle.addObserver(this)

        navigationModel.initialize(hasSavedState = savedState != null)
        navigationModel.subscribeToNavigationState { navigationData ->
            when (navigationData) {
                NavigationData.Home -> appNavigation.showFeed()
                NavigationData.Auth -> appNavigation.showOnboarding()
            }.exhaust
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        deviceLocation.init()
    }

    override fun onPause(owner: LifecycleOwner) {
        deviceLocation.stopLocationScan()
    }

    override fun onViewDetached() {
        super.onViewDetached()

        lifecycle.removeObserver(this)
        deviceLocation.stopLocationScan()
    }
}