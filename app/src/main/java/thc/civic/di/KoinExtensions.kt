package thc.civic.di

import org.koin.core.Koin
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

fun Koin.loadModule(module: Module) = loadKoinModules(listOf(module))

fun Koin.unloadModule(module: Module) = unloadKoinModules(listOf(module))