package com.civic.di

import android.content.Context
import android.location.LocationManager
import com.civic.common.android.AndroidResources
import com.civic.common.android.CommonAnimations
import com.civic.preferences.AndroidPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AndroidModule {

    fun create() = module {
        single { AndroidResources(get()) }

        single { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }

        single { AndroidPreferences("Civic") }

        factory { CommonAnimations() }
    }
}