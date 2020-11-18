package com.civic.onboarding

import com.civic.arch.NullableState
import com.civic.navigation.OnboardingModel
import com.civic.onboarding.epoxy.OnboardingEpoxyController
import org.koin.dsl.module

object OnboardingModule {

    fun create() = module {
        single { OnboardingModel(onboardingState = NullableState(null)) }
        single { OnboardingEpoxyController() }
    }
}