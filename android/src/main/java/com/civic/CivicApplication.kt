package com.civic

import android.app.Application
import com.civic.di.AndroidModule
import com.civic.di.AppModule
import com.civic.root.ActivityModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXContextTranslators

class CivicApplication : Application(), DIAware {

    override val di: DI = DI.lazy {
        import(AndroidModule.create(this@CivicApplication))

        import(AppModule.create())

        import(androidXContextTranslators)

        import(ActivityModule.create())
    }
}