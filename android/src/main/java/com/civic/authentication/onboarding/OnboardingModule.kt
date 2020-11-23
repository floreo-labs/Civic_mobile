package com.civic.authentication.onboarding

import com.civic.arch.State
import com.civic.authentication.onboarding.epoxy.OnboardingEpoxyController
import org.koin.dsl.module

object OnboardingModule {

    fun create() = module {
        single { OnboardingModel(onboardingState = State(initial = null)) }
        single { OnboardingEpoxyController() }
    }
}