package com.civic.authentication.onboarding

import com.civic.arch.State
import com.civic.arch.StateModel

class OnboardingModel(private val onboardingState: State<OnboardingData>) : StateModel() {

    fun login() {
        onboardingState.update(OnboardingData.Login)
    }

    fun anonymous() {
        onboardingState.update(OnboardingData.Anonymous)
    }
}