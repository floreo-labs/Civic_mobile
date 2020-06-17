package com.civic.di

import com.civic.arch.State
import com.civic.domain.SharedAddress
import org.koin.dsl.module

object AppModule {

    fun create() = module {
        single { State<SharedAddress?>(null) }
    }
}