package com.civic.root

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.civic.domain.SharedAddress
import com.civic.location.DeviceLocation
import com.civic.location.LocationService
import com.civic.navigation.AppNavigator
import com.civic.navigation.AppNavigation
import org.kodein.di.*
import org.kodein.di.bindings.WeakContextScope
import kotlin.math.sin

object ActivityModule {

    fun create(activity: AppCompatActivity) = DI.Module(name = "ActivityModule") {
        bind() from singleton { activity }

        bind() from singleton { activity.supportFragmentManager }

        bind() from scoped(WeakContextScope.of<Activity>()).singleton { activity.lifecycle }

        bind<LocationService>() with singleton { DeviceLocation(instance(), instance(), instance()) }

        bind<AppNavigation>() with singleton { AppNavigator(instance(), instance()) }

        bind() from provider { RootActivityDelegate(instance(), instance(), instance(), instance(), instance(), instance()) }
    }
}

