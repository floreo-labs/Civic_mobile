package com.civic.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.civic.common.android.AndroidResources
import com.civic.common.android.CommonAnimations
import org.koin.dsl.module

object AndroidModule {

    fun create(application: Application) = module {
        single { application }

        single { AndroidResources(get()) }

        single { application.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

        single { application.getSharedPreferences("Civic", Context.MODE_PRIVATE) }

        factory { CommonAnimations() }
    }
}