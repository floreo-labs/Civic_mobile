package com.civic.authentication.onboarding

sealed class OnboardingData {

    object SignUp : OnboardingData()

    object Login : OnboardingData()

    object Anonymous : OnboardingData()
}