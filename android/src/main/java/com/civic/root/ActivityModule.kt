package com.civic.root

import android.app.Activity
import com.civic.home.LocationService
import com.civic.navigation.Navigator
import com.civic.navigation.AppNavigator
import org.koin.dsl.module

object ActivityModule {

    fun create(activity: RootActivity) = module {
        single<Activity> { activity }

        single { activity.supportFragmentManager }

        single { activity.lifecycle }

        single { LocationService(get(), get(), get()) }

        single<Navigator> { AppNavigator(get()) }

        single { RootActivityDelegate(get(), get(), get(), get(), get(), get()) }
    }
}

