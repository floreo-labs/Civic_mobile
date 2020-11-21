package com.civic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.civic.common.extensions.exhaust
import com.civic.di.extensions.loadModule
import com.civic.di.extensions.unloadModule
import com.civic.home.LocationService
import com.civic.navigation.AppNavigation
import com.civic.navigation.NavigationData
import com.civic.navigation.NavigationModel
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

class RootActivity : AppCompatActivity(), KoinComponent {

    private val module by lazy {
        ActivityModule.create(this)
    }
    private val deviceLocation by inject<LocationService>()
    private val navigationModel by inject<NavigationModel>()
    private val appNavigation by inject<AppNavigation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        getKoin().loadModule(module)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        navigationModel.initialize(hasSavedState = savedInstanceState != null)
        navigationModel.subscribeToNavigationState { navigationData ->
            when (navigationData) {
                NavigationData.Home -> appNavigation.showFeed()
                NavigationData.Auth -> appNavigation.showOnboarding()
            }.exhaust
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.activity_root_fragment_container)?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        deviceLocation.init()
    }

    override fun onPause() {
        super.onPause()
        deviceLocation.stopLocationScan()
    }

    override fun onDestroy() {
        super.onDestroy()

        getKoin().unloadModule(module)
        deviceLocation.stopLocationScan()
    }
}