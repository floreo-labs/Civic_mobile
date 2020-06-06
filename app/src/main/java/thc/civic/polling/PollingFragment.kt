package thc.civic.polling

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import thc.civic.R
import thc.civic.delegate.ComponentDelegate
import thc.civic.delegate.fragment.ComponentDelegateFragment
import thc.civic.di.loadModule
import thc.civic.di.unloadModule
import thc.civic.onboarding.ONBOARDING_MODULE
import thc.civic.onboarding.OnboardingFragment

class PollingFragment: ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = PollingFragment::class.simpleName

        fun newInstance() = PollingFragment()
    }

    override val delegate: ComponentDelegate by inject<PollingFragmentDelegate>()
    override val layoutId: Int
        get() = R.layout.fragment_polling

    override fun onCreate(savedInstanceState: Bundle?) {
        getKoin().loadModule(POLLING_MODULE)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().unloadModule(POLLING_MODULE)
    }
}