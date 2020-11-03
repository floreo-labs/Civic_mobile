package com.civic.di

import com.civic.arch.State
import com.civic.home.domain.UserLocation
import org.koin.dsl.module

object AppModule {

    fun create() = module {
        single { State<UserLocation?>(null) }
    }
}