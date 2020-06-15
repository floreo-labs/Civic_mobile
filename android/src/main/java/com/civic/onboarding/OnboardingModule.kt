package com.civic.onboarding

import com.civic.onboarding.epoxy.OnboardingEpoxyController
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

object OnboardingModule {

    fun create() = DI.Module(name = "OnboardingModule") {
        bind() from provider { OnboardingEpoxyController() }
        bind() from provider { OnboardingFragmentDelegate(instance(), instance(), instance(), instance()) }
    }
}