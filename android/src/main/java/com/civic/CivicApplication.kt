package com.civic

import android.app.Application
import com.civic.di.AndroidModule
import com.civic.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CivicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CivicApplication)

            modules(
                AndroidModule.create(),
                AppModule.create()
            )
        }
    }
}