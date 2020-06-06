package thc.civic.polling

import org.koin.dsl.module
import thc.civic.onboarding.OnboardingFragmentDelegate
import thc.civic.onboarding.epoxy.OnboardingEpoxyController

val POLLING_MODULE = module {
    factory { PollingFragmentDelegate() }
}