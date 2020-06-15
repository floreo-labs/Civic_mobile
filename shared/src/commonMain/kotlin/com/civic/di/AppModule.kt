package com.civic.di

import com.civic.arch.State
import com.civic.domain.SharedAddress
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

object AppModule {

    fun create() = DI.Module(name = "AppModule", init = {
        bind() from singleton { State<SharedAddress?>(null) }
    })
}