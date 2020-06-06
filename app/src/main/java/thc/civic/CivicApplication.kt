package thc.civic

import android.app.Application
import org.koin.core.context.startKoin
import thc.civic.di.androidAppModule
import thc.civic.di.appModule

class CivicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule, androidAppModule(this@CivicApplication))
        }
    }
}