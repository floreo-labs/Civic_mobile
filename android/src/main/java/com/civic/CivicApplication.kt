package com.civic

import android.app.Application
import com.civic.di.AndroidModule
import com.civic.di.AppModule
import org.koin.core.context.startKoin

class CivicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                AndroidModule.create(this@CivicApplication),
                AppModule.create()
            )
        }
    }
}