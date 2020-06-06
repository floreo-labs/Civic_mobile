package thc.civic.di

import org.koin.dsl.module
import thc.civic.common.android.AndroidResources

val appModule = module {
    single { AndroidResources(get()) }
}