package com.civic.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.civic.common.android.AndroidPreferences
import com.civic.common.android.AndroidResources
import com.civic.common.android.CommonAnimations
import com.civic.preferences.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AndroidModule {

    fun create() = module {
        single { AndroidResources(get()) }

        single { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }

        single<Preferences> { AndroidPreferences(context = get<Application>(), name = "Civic")}

        factory { CommonAnimations() }
    }
}