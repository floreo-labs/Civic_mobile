package com.civic.root

import androidx.appcompat.app.AppCompatActivity
import com.civic.location.DeviceLocation
import com.civic.location.LocationService
import com.civic.navigation.AppNavigator
import org.koin.dsl.module

object ActivityModule {

    fun create(activity: AppCompatActivity) = module {
        single { activity }

        single { activity.supportFragmentManager }

        single { activity.lifecycle }

        single<LocationService> { DeviceLocation(get(), get()) }

        single { AppNavigator(get()) }

        factory { RootActivityDelegate(get(), get(), get(), get(), get(), get()) }
    }
}

