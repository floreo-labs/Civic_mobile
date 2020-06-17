package com.civic.di.extensions

import org.koin.core.Koin
import org.koin.core.module.Module

fun Koin.loadModule(module: Module) = loadModules(listOf(module))

fun Koin.unloadModule(module: Module) = unloadModules(listOf(module))