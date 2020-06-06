package thc.civic.onboarding

import org.koin.dsl.module
import thc.civic.common.android.CommonAnimations
import thc.civic.onboarding.epoxy.OnboardingEpoxyController

val ONBOARDING_MODULE = module {
    factory { OnboardingEpoxyController() }
    factory { OnboardingFragmentDelegate(get(), get(), get(), get(), get()) }
}