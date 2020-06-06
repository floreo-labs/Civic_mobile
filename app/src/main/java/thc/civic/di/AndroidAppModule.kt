package thc.civic.di

import android.app.Application
import android.content.Context
import org.koin.dsl.module
import thc.civic.common.android.CommonAnimations
import thc.civic.common.android.Preferences
import kotlin.math.sin

fun androidAppModule(application: Application) = module {
    single { application }

    single {
        val sharedPreferences = application.getSharedPreferences("Civic", Context.MODE_PRIVATE)
        Preferences(sharedPreferences)
    }

    factory { CommonAnimations() }
}