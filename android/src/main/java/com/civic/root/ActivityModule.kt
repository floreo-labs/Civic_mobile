package com.civic.root

import androidx.appcompat.app.AppCompatActivity
import com.civic.location.DeviceLocation
import com.civic.location.LocationService
import com.civic.navigation.AppNavigation
import com.civic.navigation.AppNavigator
import org.kodein.di.DI
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton

object ActivityModule {

    fun create() = DI.Module(name = "ActivityModule") {
        val scoped = scoped(AndroidLifecycleScope.singleItem)

        bind<AppCompatActivity>() with scoped.singleton { context as AppCompatActivity }

        bind() from scoped.singleton { instance<AppCompatActivity>().supportFragmentManager }

        bind() from scoped.singleton { context.lifecycle }

        bind<LocationService>() with scoped.singleton { DeviceLocation(instance(), instance()) }

        bind<AppNavigation>() with scoped.singleton { AppNavigator(instance()) }

        bind<RootActivityDelegate>() with scoped.singleton { RootActivityDelegate(instance(), instance(), instance(), instance(), instance(), instance()) }
    }
}

