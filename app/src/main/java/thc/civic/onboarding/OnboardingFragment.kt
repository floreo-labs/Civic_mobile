package thc.civic.onboarding

import android.os.Bundle
import org.koin.core.KoinComponent
import org.koin.core.inject
import thc.civic.R
import thc.civic.delegate.fragment.ComponentDelegateFragment
import thc.civic.di.loadModule
import thc.civic.di.unloadModule

class OnboardingFragment : ComponentDelegateFragment(), KoinComponent {

    override val delegate by inject<OnboardingFragmentDelegate>()
    override val layoutId: Int
        get() = R.layout.fragment_onboarding

    companion object {
        val TAG = OnboardingFragment::class.simpleName

        fun newInstance() = OnboardingFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getKoin().loadModule(ONBOARDING_MODULE)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().unloadModule(ONBOARDING_MODULE)
    }
}