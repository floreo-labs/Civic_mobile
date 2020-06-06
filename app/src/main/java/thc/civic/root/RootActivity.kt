package thc.civic.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module
import thc.civic.R
import thc.civic.di.loadModule
import thc.civic.di.unloadModule

class RootActivity : AppCompatActivity(), KoinComponent {

    private var module : Module? = null

    private val delegate by inject<RootActivityDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        module = rootModule(this).also { rootModule ->
            getKoin().loadModule(rootModule)
        }
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        delegate.bind(findViewById(R.id.activity_root), savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        module?.let { rootModule ->
            getKoin().unloadModule(rootModule)
        }
        module = null
    }
}