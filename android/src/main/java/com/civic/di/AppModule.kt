package com.civic.di

import com.civic.arch.NullableState
import com.civic.feature.home.domain.UserLocation
import org.koin.dsl.module

object AppModule {

    fun create() = module {
        single { NullableState<UserLocation?>(null) }
    }
}