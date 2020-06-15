package com.civic.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.civic.common.android.AndroidResources
import com.civic.common.android.CommonAnimations
import org.kodein.di.*

object AndroidModule {

    fun create(application: Application) = DI.Module(name = "AndroidModule") {
        bind() from singleton { application }

        bind() from singleton { AndroidResources(instance()) }

        bind<LocationManager>() with singleton { application.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

        bind() from  singleton { application.getSharedPreferences("Civic", Context.MODE_PRIVATE) }

        bind<CommonAnimations>() with provider { CommonAnimations() }
    }
}