package com.civic.navigation

import com.civic.arch.NullableState
import com.civic.arch.StateModel

class OnboardingModel(private val onboardingState: NullableState<OnboardingData>) : StateModel() {

    fun signup() {
        onboardingState.update(OnboardingData.SignUp)
    }

    fun login() {
        onboardingState.update(OnboardingData.Login)
    }

    fun anonymous() {
        onboardingState.update(OnboardingData.Anonymous)
    }
}