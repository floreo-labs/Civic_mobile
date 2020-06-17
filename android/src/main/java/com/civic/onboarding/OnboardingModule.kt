package com.civic.onboarding

import com.civic.onboarding.epoxy.OnboardingEpoxyController
import org.koin.dsl.module

object OnboardingModule {

    fun create() = module {
        single { OnboardingEpoxyController() }
        single { OnboardingFragmentDelegate(get(), get(), get(), get()) }
    }
}