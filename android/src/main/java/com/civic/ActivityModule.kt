package com.civic

import android.app.Activity
import com.civic.arch.State
import com.civic.home.LocationService
import com.civic.navigation.AppNavigation
import com.civic.navigation.AppNavigator
import com.civic.navigation.NavigationModel
import org.koin.dsl.module

object ActivityModule {

    fun create(activity: RootActivity) = module {
        single<Activity> { activity }

        single { activity.supportFragmentManager }

        single { activity.lifecycle }

        single { LocationService(get(), get(), get()) }

        single<AppNavigation> { AppNavigator(get()) }

        single {
            NavigationModel(
                preferences = get(),
                navigationState = State(initial = null)
            )
        }
    }
}

