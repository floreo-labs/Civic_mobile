package com.civic.root

import android.app.Activity
import com.civic.location.DeviceLocation
import com.civic.location.LocationService
import com.civic.navigation.AppNavigation
import com.civic.navigation.AppNavigator
import org.koin.dsl.module

object ActivityModule {

    fun create(activity: RootActivity) = module {
        single<Activity> { activity }

        single { activity.supportFragmentManager }

        single { activity.lifecycle }

        single<LocationService> { DeviceLocation(get(), get(), get()) }

        single<AppNavigation> { AppNavigator(get()) }

        single { RootActivityDelegate(get(), get(), get(), get(), get(), get()) }
    }
}

